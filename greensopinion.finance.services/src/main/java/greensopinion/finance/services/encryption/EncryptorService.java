/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.encryption;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import javax.inject.Inject;

import greensopinion.finance.services.domain.EncryptorSettings;
import greensopinion.finance.services.domain.Settings;
import greensopinion.finance.services.domain.SettingsService;

public class EncryptorService {

	private final SettingsService settingsService;
	private final EncryptorProviderService encryptorProviderService;
	private final Object configurationLock = new Object();
	private final EncryptorListener encryptorListener;

	@Inject
	public EncryptorService(SettingsService settingsService, EncryptorProviderService encryptorProviderService,
			EncryptorListener encryptorListener) {
		this.settingsService = checkNotNull(settingsService);
		this.encryptorProviderService = checkNotNull(encryptorProviderService);
		this.encryptorListener = checkNotNull(encryptorListener);
	}

	public boolean isConfigured() {
		return settingsService.retrieve().getEncryptorSettings() != null;
	}

	public boolean isInitialized() {
		return encryptorProviderService.isInitialized();
	}

	public void reconfigure(String newMasterPassword) {
		checkMasterPassword(newMasterPassword);
		synchronized (configurationLock) {
			checkState(isConfigured(), "Encryption must be configured to reset the master password");
			checkState(isInitialized(), "Encryption must be initialized to reset the master password");

			encryptorListener.aboutToChangeEncryptor();
			setMasterPassword(newMasterPassword);
			encryptorListener.encryptorChanged();
		}
	}

	public void configure(String masterPassword) {
		checkMasterPassword(masterPassword);
		synchronized (configurationLock) {
			checkState(!isConfigured(), "Cannot configure encryption settings more than once");

			setMasterPassword(masterPassword);
		}
	}

	private void checkMasterPassword(String masterPassword) {
		checkNotNull(masterPassword, "Must provide a master password");
		checkArgument(!masterPassword.isEmpty(), "Must provide a master password");
		checkArgument(masterPassword.equals(masterPassword.trim()),
				"Master password must not have leading or trailing whitespace");
	}

	private void setMasterPassword(String masterPassword) {
		EncryptorSettings encryptorSettings = EncryptorSettings.newSettings(masterPassword);

		Settings settings = settingsService.retrieve();
		settings = settings.withEncryptorSettings(encryptorSettings);

		encryptorProviderService.setEncryptor(new Encryptor(encryptorSettings, masterPassword));
		settingsService.update(settings);
	}

	public void initialize(String masterPassword) {
		checkNotNull(masterPassword, "Must provide a master password");
		checkArgument(!masterPassword.isEmpty(), "Must provide a master password");
		synchronized (configurationLock) {
			checkState(!isInitialized(), "Cannot initialize encryption settings more than once");
			checkState(isConfigured(), "Must configure encryption settings before initializing");

			EncryptorSettings encryptorSettings = settingsService.retrieve().getEncryptorSettings();
			if (!encryptorSettings.validateMasterPassword(masterPassword)) {
				throw new InvalidMasterPasswordException();
			}
			encryptorProviderService.setEncryptor(new Encryptor(encryptorSettings, masterPassword));
		}
	}
}

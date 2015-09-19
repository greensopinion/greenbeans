package greensopinion.finance.services.encryption;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import javax.inject.Inject;

import greensopinion.finance.services.data.Settings;
import greensopinion.finance.services.data.SettingsService;

public class EncryptorService {

	private final SettingsService settingsService;
	private final EncryptorProviderService encryptorProviderService;
	private final Object configurationLock = new Object();

	@Inject
	public EncryptorService(SettingsService settingsService, EncryptorProviderService encryptorProviderService) {
		this.settingsService = checkNotNull(settingsService);
		this.encryptorProviderService = checkNotNull(encryptorProviderService);
	}

	public boolean isConfigured() {
		return settingsService.retrieve().getEncryptorSettings() != null;
	}

	public boolean isInitialized() {
		return encryptorProviderService.isInitialized();
	}

	public void configure(String masterPassword) {
		checkNotNull(masterPassword, "Must provide a master password");
		checkArgument(!masterPassword.isEmpty(), "Must provide a master password");
		checkArgument(masterPassword.equals(masterPassword.trim()),
				"Master password must not have leading or trailing whitespace");
		synchronized (configurationLock) {
			checkState(!isConfigured(), "Cannot configure encryption settings more than once");

			EncryptorSettings encryptorSettings = EncryptorSettings.newSettings(masterPassword);
			encryptorProviderService.setEncryptor(new Encryptor(encryptorSettings, masterPassword));

			Settings settings = settingsService.retrieve();
			settings = settings.withEncryptorSettings(encryptorSettings);
			settingsService.update(settings);
		}
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

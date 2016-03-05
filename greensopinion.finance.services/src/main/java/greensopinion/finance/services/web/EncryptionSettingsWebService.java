/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import greensopinion.finance.services.encryption.EncryptorService;
import greensopinion.finance.services.web.model.EncryptionSettings;
import greensopinion.finance.services.web.model.NewEncryptionSettings;
import greensopinion.finance.services.web.model.ResetEncryptionSettings;

@Path(EncryptionSettingsWebService.BASE_PATH)
public class EncryptionSettingsWebService {
	static final String BASE_PATH = "/encryption-settings";

	static final String PATH_CURRENT = "current";

	private final EncryptorService encryptorService;

	@Inject
	EncryptionSettingsWebService(EncryptorService encryptorService) {
		this.encryptorService = encryptorService;
	}

	@Path(PATH_CURRENT)
	@GET
	public EncryptionSettings get() {
		return new EncryptionSettings(encryptorService.isConfigured(), encryptorService.isInitialized());
	}

	@Path(PATH_CURRENT)
	@PUT
	public void configure(NewEncryptionSettings encryptionSettings) {
		encryptorService.configure(encryptionSettings.getMasterPassword());
	}

	@Path(PATH_CURRENT)
	@POST
	public void initialize(NewEncryptionSettings encryptionSettings) {
		encryptorService.initialize(encryptionSettings.getMasterPassword());
	}

	@PUT
	public void configure(ResetEncryptionSettings encryptionSettings) {
		encryptorService.initialize(encryptionSettings.getMasterPassword());
		encryptorService.reconfigure(encryptionSettings.getNewMasterPassword());
	}
}

/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

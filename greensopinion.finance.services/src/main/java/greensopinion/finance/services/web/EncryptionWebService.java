package greensopinion.finance.services.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import greensopinion.finance.services.encryption.EncryptorService;
import greensopinion.finance.services.model.EncryptionSettings;

@Path(EncryptionWebService.BASE_PATH)
public class EncryptionWebService {
	static final String BASE_PATH = "/encryption-settings";

	static final String PATH_CURRENT = "current";

	private EncryptorService encryptorService;

	@Path(PATH_CURRENT)
	@GET
	public EncryptionSettings get() {
		return new EncryptionSettings(encryptorService.isConfigured(), encryptorService.isInitialized());
	}
}

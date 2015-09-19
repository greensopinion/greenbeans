package greensopinion.finance.services.persistence;

import greensopinion.finance.services.domain.EncryptorSettings;
import greensopinion.finance.services.encryption.Encryptor;
import greensopinion.finance.services.encryption.EncryptorProviderService;

public class MockEncryptorProviderService {

	public static EncryptorProviderService create() {
		EncryptorProviderService encryptorProviderService = new EncryptorProviderService();

		String masterPassword = "test 123";
		Encryptor encryptor = new Encryptor(EncryptorSettings.newSettings(masterPassword), masterPassword);
		encryptorProviderService.setEncryptor(encryptor);
		return encryptorProviderService;
	}
}

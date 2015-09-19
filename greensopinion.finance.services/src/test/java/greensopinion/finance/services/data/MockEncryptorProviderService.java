package greensopinion.finance.services.data;

import greensopinion.finance.services.encryption.Encryptor;
import greensopinion.finance.services.encryption.EncryptorProviderService;
import greensopinion.finance.services.encryption.EncryptorSettings;

public class MockEncryptorProviderService {

	public static EncryptorProviderService create() {
		EncryptorProviderService encryptorProviderService = new EncryptorProviderService();

		String masterPassword = "test 123";
		Encryptor encryptor = new Encryptor(EncryptorSettings.newSettings(masterPassword), masterPassword);
		encryptorProviderService.setEncryptor(encryptor);
		return encryptorProviderService;
	}
}

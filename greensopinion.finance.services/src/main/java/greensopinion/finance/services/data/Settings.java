package greensopinion.finance.services.data;

import greensopinion.finance.services.encryption.EncryptorSettings;

public class Settings {
	private EncryptorSettings encryptorSettings;

	public Settings(EncryptorSettings encryptorSettings) {
		this.encryptorSettings = encryptorSettings;
	}

	public Settings() {
	}

	public EncryptorSettings getEncryptorSettings() {
		return encryptorSettings;
	}

	public Settings withEncryptorSettings(EncryptorSettings encryptorSettings) {
		return new Settings(encryptorSettings);
	}
}

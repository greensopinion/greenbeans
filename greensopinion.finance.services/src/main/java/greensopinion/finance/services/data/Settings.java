package greensopinion.finance.services.data;

import greensopinion.finance.services.encryption.EncryptorSettings;

public class Settings {
	private EncryptorSettings encryptorSettings;

	public void setEncryptorSettings(EncryptorSettings encryptorSettings) {
		this.encryptorSettings = encryptorSettings;
	}

	public EncryptorSettings getEncryptorSettings() {
		return encryptorSettings;
	}

}

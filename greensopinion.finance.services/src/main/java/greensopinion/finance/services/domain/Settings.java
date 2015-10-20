package greensopinion.finance.services.domain;

public class Settings {
	private boolean userHasAgreedToLicense;
	private EncryptorSettings encryptorSettings;

	public Settings(EncryptorSettings encryptorSettings, boolean userHasAgreedToLicense) {
		this.encryptorSettings = encryptorSettings;
		this.userHasAgreedToLicense = userHasAgreedToLicense;
	}

	public Settings() {
	}

	public EncryptorSettings getEncryptorSettings() {
		return encryptorSettings;
	}

	public boolean userHasAgreedToLicense() {
		return userHasAgreedToLicense;
	}

	public Settings withUserHasAgreedToLicense(boolean userHasAgreedToLicense) {
		return new Settings(encryptorSettings, userHasAgreedToLicense);
	}

	public Settings withEncryptorSettings(EncryptorSettings encryptorSettings) {
		return new Settings(encryptorSettings, userHasAgreedToLicense);
	}
}

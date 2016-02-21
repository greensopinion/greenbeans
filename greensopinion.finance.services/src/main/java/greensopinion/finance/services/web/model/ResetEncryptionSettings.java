package greensopinion.finance.services.web.model;

public class ResetEncryptionSettings {
	private String masterPassword;
	private String newMasterPassword;

	public ResetEncryptionSettings() {
	}

	public ResetEncryptionSettings(String masterPassword, String newMasterPassword) {
		this.masterPassword = masterPassword;
		this.newMasterPassword = newMasterPassword;
	}

	public String getMasterPassword() {
		return masterPassword;
	}

	public String getNewMasterPassword() {
		return newMasterPassword;
	}
}

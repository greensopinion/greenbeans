package greensopinion.finance.services.encryption;

import java.security.SecureRandom;
import java.util.Arrays;

public class EncryptorSettings {
	private static final String KNOWN_STATE = "9f682f9b-0a9a-4e15-9111-09276247c658";

	public static EncryptorSettings newSettings(String masterPassword) {
		byte[] salt = new byte[8];
		new SecureRandom().nextBytes(salt);
		String encryptedState = new Encryptor(new EncryptorSettings(null, salt), masterPassword).encrypt(KNOWN_STATE);
		return new EncryptorSettings(encryptedState, salt);
	}

	private String masterPasswordVerificationState;
	private byte[] salt;

	public EncryptorSettings(String masterPasswordVerificationState, byte[] salt) {
		this.masterPasswordVerificationState = masterPasswordVerificationState;
		this.salt = Arrays.copyOf(salt, salt.length);
	}

	public String getMasterPasswordVerificationState() {
		return masterPasswordVerificationState;
	}

	public byte[] getSalt() {
		return salt;
	}

	public boolean validateMasterPassword(String masterPassword) {
		try {
			String decryptedState = new Encryptor(new EncryptorSettings(null, salt), masterPassword)
					.decrypt(masterPasswordVerificationState);
			return KNOWN_STATE.equals(decryptedState);
		} catch (IllegalStateException e) {
			return false;
		}
	}
}

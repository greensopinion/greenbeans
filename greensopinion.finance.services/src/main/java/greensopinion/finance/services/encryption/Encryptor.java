package greensopinion.finance.services.encryption;

import java.nio.charset.StandardCharsets;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;

public class Encryptor {

	private BytesEncryptor encryptor;

	public Encryptor(EncryptorSettings settings, String masterPassword) {
		encryptor = Encryptors.stronger(masterPassword, new String(Hex.encode(settings.getSalt())));
	}

	public String encrypt(String data) {
		if (data == null) {
			return null;
		}
		return new String(Hex.encode(encryptor.encrypt(data.getBytes(StandardCharsets.UTF_8))));
	}

	public String decrypt(String data) {
		if (data == null) {
			return null;
		}
		byte[] decodedBytes = Hex.decode(data);
		byte[] decryptedBytes = encryptor.decrypt(decodedBytes);
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}
}

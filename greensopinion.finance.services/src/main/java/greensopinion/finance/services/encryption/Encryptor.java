/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.encryption;

import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.charset.StandardCharsets;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;

import greensopinion.finance.services.domain.EncryptorSettings;

public class Encryptor {

	private final BytesEncryptor encryptor;

	public Encryptor(EncryptorSettings settings, String masterPassword) {
		this(Encryptors.stronger(masterPassword, new String(Hex.encode(settings.getSalt()))));
	}

	public Encryptor(BytesEncryptor bytesEncryptor) {
		this.encryptor = checkNotNull(bytesEncryptor);
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

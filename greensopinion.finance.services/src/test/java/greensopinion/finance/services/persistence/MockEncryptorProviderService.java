/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.persistence;

import org.springframework.security.crypto.encrypt.BytesEncryptor;

import greensopinion.finance.services.encryption.Encryptor;
import greensopinion.finance.services.encryption.EncryptorProviderService;

public class MockEncryptorProviderService {

	static class NoOpBytesEncryptor implements BytesEncryptor {
		@Override
		public byte[] encrypt(byte[] byteArray) {
			return byteArray;
		}

		@Override
		public byte[] decrypt(byte[] encryptedByteArray) {
			return encryptedByteArray;
		}
	}

	public static EncryptorProviderService create() {
		EncryptorProviderService encryptorProviderService = new EncryptorProviderService();
		Encryptor encryptor = new Encryptor(new NoOpBytesEncryptor());
		encryptorProviderService.setEncryptor(encryptor);
		return encryptorProviderService;
	}
}

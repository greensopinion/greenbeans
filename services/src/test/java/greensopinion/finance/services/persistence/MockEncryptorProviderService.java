/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

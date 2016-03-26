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
package greensopinion.finance.services.domain;

import java.security.SecureRandom;
import java.util.Arrays;

import greensopinion.finance.services.encryption.Encryptor;

public class EncryptorSettings {
	private static final String KNOWN_STATE = "9f682f9b-0a9a-4e15-9111-09276247c658";

	public static EncryptorSettings newSettings(String masterPassword) {
		byte[] salt = new byte[8];
		new SecureRandom().nextBytes(salt);
		String encryptedState = new Encryptor(new EncryptorSettings(null, salt), masterPassword).encrypt(KNOWN_STATE);
		return new EncryptorSettings(encryptedState, salt);
	}

	private final String masterPasswordVerificationState;
	private final byte[] salt;

	public EncryptorSettings(String masterPasswordVerificationState, byte[] salt) {
		this.masterPasswordVerificationState = masterPasswordVerificationState;
		this.salt = Arrays.copyOf(salt, salt.length);
	}

	public String getMasterPasswordVerificationState() {
		return masterPasswordVerificationState;
	}

	public byte[] getSalt() {
		return Arrays.copyOf(salt, salt.length);
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

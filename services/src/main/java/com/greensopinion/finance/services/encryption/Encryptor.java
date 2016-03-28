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
package com.greensopinion.finance.services.encryption;

import static com.google.common.base.Preconditions.checkNotNull;

import java.nio.charset.StandardCharsets;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;

import com.greensopinion.finance.services.domain.EncryptorSettings;

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

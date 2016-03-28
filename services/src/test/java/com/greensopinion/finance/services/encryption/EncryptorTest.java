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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.greensopinion.finance.services.domain.EncryptorSettings;
import com.greensopinion.finance.services.encryption.Encryptor;

public class EncryptorTest {
	private EncryptorSettings encryptorSettings = EncryptorSettings.newSettings("12345");
	private Encryptor encryptor = new Encryptor(encryptorSettings, "12345");

	@Test
	public void roundTrip() {
		String testData = "d749f39f-d0dc-4092-bf35-fa8442790a7b";
		String encrypted = encryptor.encrypt(testData);
		assertNotNull(encrypted);
		assertFalse(encrypted.equals(testData));
		String decrypted = encryptor.decrypt(encrypted);
		assertEquals(testData, decrypted);
	}

	@Test
	public void roundTripNull() {
		assertNull(encryptor.encrypt(null));
		assertNull(encryptor.decrypt(null));
	}
}

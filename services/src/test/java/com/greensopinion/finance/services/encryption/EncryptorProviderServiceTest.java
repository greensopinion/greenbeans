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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.greensopinion.finance.services.encryption.Encryptor;
import com.greensopinion.finance.services.encryption.EncryptorProviderService;

public class EncryptorProviderServiceTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	private final EncryptorProviderService service = new EncryptorProviderService();

	@Test
	public void isInitialized() {
		assertFalse(service.isInitialized());
		service.setEncryptor(mock(Encryptor.class));
		assertTrue(service.isInitialized());
	}

	@Test
	public void getEncryptorNotInitialized() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Encryptor is not initialized");
		service.getEncryptor();
	}

	@Test
	public void setEncryptor() {
		Encryptor encryptor = mock(Encryptor.class);
		service.setEncryptor(encryptor);
		assertSame(encryptor, service.getEncryptor());

		Encryptor encryptor2 = mock(Encryptor.class);
		service.setEncryptor(encryptor2);
		assertSame(encryptor2, service.getEncryptor());
	}
}

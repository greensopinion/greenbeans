/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.encryption;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

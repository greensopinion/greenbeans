/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import greensopinion.finance.services.domain.EncryptorSettings;

public class EncryptorSettingsTest {
	@Test
	public void masterPasswordValidationState() {
		EncryptorSettings settings = EncryptorSettings.newSettings("12345");
		assertNotNull(settings.getMasterPasswordVerificationState());
		assertSaltInitialized(settings);

		for (int x = 12346; x < 12400; ++x) {
			assertFalse(settings.validateMasterPassword(Integer.toString(x)));
		}
		assertTrue(settings.validateMasterPassword("12345"));
	}

	private void assertSaltInitialized(EncryptorSettings settings) {
		assertNotNull(settings.getSalt());
		assertEquals(8, settings.getSalt().length);
		int zeros = 0;
		for (byte b : settings.getSalt()) {
			if (b == 0) {
				++zeros;
			}
		}
		assertTrue(zeros < 8);
	}
}

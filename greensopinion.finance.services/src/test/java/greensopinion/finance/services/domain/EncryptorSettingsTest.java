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

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
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class SettingsTest {

	@Test
	public void defaults() {
		Settings settings = new Settings();
		assertFalse(settings.userHasAgreedToLicense());
		assertEquals(null, settings.getEncryptorSettings());
	}

	@Test
	public void userHasAgreedToLicense() {
		Settings settings = new Settings(null, false);
		assertEquals(false, settings.userHasAgreedToLicense());
		settings = settings.withUserHasAgreedToLicense(true);
		assertEquals(true, settings.userHasAgreedToLicense());
	}

	@Test
	public void encryptorSettings() {
		Settings settings = new Settings(null, false);
		assertEquals(false, settings.userHasAgreedToLicense());
		EncryptorSettings encryptorSettings = mock(EncryptorSettings.class);
		settings = settings.withUserHasAgreedToLicense(true).withEncryptorSettings(encryptorSettings);
		assertEquals(true, settings.userHasAgreedToLicense());
		assertSame(encryptorSettings, settings.getEncryptorSettings());
	}
}

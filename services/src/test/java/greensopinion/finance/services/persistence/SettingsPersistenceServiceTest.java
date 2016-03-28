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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.io.Files;

import greensopinion.finance.services.TestResources;
import greensopinion.finance.services.domain.EncryptorSettings;
import greensopinion.finance.services.domain.Settings;

public class SettingsPersistenceServiceTest {

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File dataFolder;
	private SettingsPersistenceService service;

	private DataDirectoryLocator dataDirectory;

	@Before
	public void before() {
		dataFolder = new File(temporaryFolder.getRoot(), "data");
		dataDirectory = new DataDirectoryLocator() {
			@Override
			public File locate() {
				return dataFolder;
			}
		};
		service = createService();
	}

	@Test
	public void settingsRoundTrip() throws IOException {
		assertFalse(dataFolder.exists());

		Settings data = new Settings(new EncryptorSettings("1234", new byte[] { 35, 36 }), true);

		service.save(data);
		assertTrue(dataFolder.exists());

		assertEquals(TestResources.load(SettingsPersistenceServiceTest.class, "expected-settings.json.txt"),
				Files.toString(new File(dataFolder, "settings.json"), StandardCharsets.UTF_8));

		Settings loaded = service.load();
		assertNotNull(loaded);
		assertEquals(data.userHasAgreedToLicense(), loaded.userHasAgreedToLicense());
		assertEquals(data.getEncryptorSettings().getMasterPasswordVerificationState(),
				loaded.getEncryptorSettings().getMasterPasswordVerificationState());
		assertArrayEquals(data.getEncryptorSettings().getSalt(), loaded.getEncryptorSettings().getSalt());
	}

	private SettingsPersistenceService createService() {
		return new SettingsPersistenceService(MockPersistenceGsonProvider.create(), dataDirectory);
	}
}

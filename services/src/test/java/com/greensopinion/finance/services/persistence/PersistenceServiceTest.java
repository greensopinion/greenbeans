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
package com.greensopinion.finance.services.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.greensopinion.finance.services.persistence.DataDirectoryLocator;
import com.greensopinion.finance.services.persistence.PersistenceService;

public class PersistenceServiceTest {
	public static class TestConfig {
		String value;

		public TestConfig(String value) {
			this.value = value;
		}
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File dataFolder;
	private PersistenceService<TestConfig> service;

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
	public void readSettingsNoFile() {
		assertNotNull(service.load());
	}

	@Test
	public void settingsRoundTrip() throws IOException {
		assertFalse(dataFolder.exists());

		TestConfig data = new TestConfig("a");
		service.save(data);

		TestConfig loaded = service.load();
		assertNotNull(loaded);
		assertEquals("a", loaded.value);
	}

	private PersistenceService<TestConfig> createService() {
		return new PersistenceService<TestConfig>(MockPersistenceGsonProvider.create(), dataDirectory,
				TestConfig.class) {

			@Override
			String getFilename() {
				return "test.json";
			}

			@Override
			TestConfig defaultInstance() {
				return new TestConfig("Default");
			}
		};
	}
}

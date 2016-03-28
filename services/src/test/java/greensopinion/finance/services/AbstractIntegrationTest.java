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
package greensopinion.finance.services;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import greensopinion.finance.services.application.Main;
import greensopinion.finance.services.encryption.EncryptorService;
import greensopinion.finance.services.persistence.DataDirectoryLocator;

public class AbstractIntegrationTest {
	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();
	protected Injector injector;

	@Before
	public void before() {
		injector = createInjector();
		initializeEncryptor();
	}

	protected Injector createInjector() {
		return Guice.createInjector(Modules.override(Main.applicationModules()).with(dataDirectoryLocatorModule()));
	}

	private Module dataDirectoryLocatorModule() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				bind(DataDirectoryLocator.class).toInstance(new DataDirectoryLocator() {
					@Override
					public File locate() {
						return temporaryFolder.getRoot();
					}
				});
			}
		};
	}

	private void initializeEncryptor() {
		EncryptorService encryptor = injector.getInstance(EncryptorService.class);
		encryptor.configure("test");
	}
}

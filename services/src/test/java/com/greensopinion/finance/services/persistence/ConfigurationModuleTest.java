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

import static com.greensopinion.finance.services.InjectorAsserts.assertSingletonBinding;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.greensopinion.finance.services.domain.EntityEventSupport;
import com.greensopinion.finance.services.domain.SettingsService;
import com.greensopinion.finance.services.domain.TransactionsService;
import com.greensopinion.finance.services.persistence.ConfigurationModule;
import com.greensopinion.finance.services.persistence.DataDirectoryLocator;
import com.greensopinion.finance.services.persistence.SettingsPersistenceService;
import com.greensopinion.finance.services.persistence.TransactionsPersistenceService;

public class ConfigurationModuleTest {

	@Test
	public void providesSettingsPersistenceService() {
		assertSingletonBinding(createInjector(), SettingsPersistenceService.class);
	}

	@Test
	public void providesSettingsService() {
		assertSingletonBinding(createInjector(), SettingsService.class);
	}

	@Test
	public void providesTransactionsPersistenceService() {
		assertSingletonBinding(createInjector(), TransactionsPersistenceService.class);
	}

	@Test
	public void providesTransactionsService() {
		assertSingletonBinding(createInjector(), TransactionsService.class);
	}

	@Test
	public void providesDataDirectoryLocator() {
		assertSingletonBinding(createInjector(), DataDirectoryLocator.class);
	}

	@Test
	public void providesEntityEventSupport() {
		assertSingletonBinding(createInjector(), EntityEventSupport.class);
	}

	private Injector createInjector() {
		return Guice.createInjector(new ConfigurationModule());
	}
}

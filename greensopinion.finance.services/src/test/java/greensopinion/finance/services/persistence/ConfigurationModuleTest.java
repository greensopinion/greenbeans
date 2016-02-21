/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.persistence;

import static greensopinion.finance.services.InjectorAsserts.assertSingletonBinding;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import greensopinion.finance.services.domain.EntityEventSupport;
import greensopinion.finance.services.domain.SettingsService;
import greensopinion.finance.services.domain.TransactionsService;

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

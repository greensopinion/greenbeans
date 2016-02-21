/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web;

import static greensopinion.finance.services.InjectorAsserts.assertSingletonBinding;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import greensopinion.finance.services.ImportFilesService;
import greensopinion.finance.services.encryption.EncryptorService;
import greensopinion.finance.services.web.dispatch.Invoker;
import greensopinion.finance.services.web.dispatch.WebDispatch;

public class WebServiceModuleTest {
	@Test
	public void providesWebDispatch() {
		assertSingletonBinding(createInjector(), WebDispatch.class);
	}

	@Test
	public void providesGsonWebRenderer() {
		assertSingletonBinding(createInjector(), GsonWebRenderer.class);
	}

	@Test
	public void providesInvoker() {
		assertSingletonBinding(createInjector(), Invoker.class);
	}

	@Test
	public void providesAboutWebService() {
		assertSingletonBinding(createInjector(), AboutWebService.class);
	}

	@Test
	public void providesEncryptionSettingsWebService() {
		assertSingletonBinding(createInjector(), EncryptionSettingsWebService.class);
	}

	@Test
	public void providesImportWebService() {
		assertSingletonBinding(createInjector(), ImportFilesWebService.class);
	}

	@Test
	public void providesReportsWebService() {
		assertSingletonBinding(createInjector(), ReportsWebService.class);
	}

	@Test
	public void providesCategoryWebService() {
		assertSingletonBinding(createInjector(), CategoryWebService.class);
	}

	@Test
	public void providesTransactionsWebService() {
		assertSingletonBinding(createInjector(), TransactionsWebService.class);
	}

	@Test
	public void providesEulaWebService() {
		assertSingletonBinding(createInjector(), EulaWebService.class);
	}

	@Test
	public void providesGson() {
		Gson gson = assertSingletonBinding(createInjector(), Gson.class);
		assertHtmlEscapingDisabled(gson);
		assertDate(gson);
	}

	private void assertDate(Gson gson) {
		assertEquals("\"2015-09-19T16:47:00.000Z\"", gson.toJson(new Date(1442681220000L)));
	}

	private void assertHtmlEscapingDisabled(Gson gson) {
		assertEquals("\"'\"", gson.toJson("'"));
	}

	private Injector createInjector() {
		return Guice.createInjector(new WebServiceModule(), new AbstractModule() {
			@Override
			protected void configure() {
				bind(ImportFilesService.class).toInstance(mock(ImportFilesService.class));
				bind(EncryptorService.class).toInstance(mock(EncryptorService.class));
			}
		});
	}
}

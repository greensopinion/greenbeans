/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.persistence;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;

import greensopinion.finance.services.domain.EntityEventSupport;

public class ConfigurationServiceTest {
	public static class TestConfig {
		String value;

		public TestConfig(String value) {
			this.value = value;
		}
	}

	private PersistenceService<TestConfig> persistenceService;
	private ConfigurationService<TestConfig> configurationService;
	private TestConfig data;
	private EntityEventSupport eventSupport;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		data = new TestConfig("a");
		persistenceService = mock(PersistenceService.class);
		doReturn(data).when(persistenceService).load();

		eventSupport = spy(new EntityEventSupport());
		configurationService = new ConfigurationService<TestConfig>(persistenceService, eventSupport);
	}

	@Test
	public void retrieve() {
		assertSame(data, configurationService.retrieve());
		assertSame(data, configurationService.retrieve());
		verifyNoMoreInteractions(eventSupport);
	}

	@Test
	public void clearState() {
		assertSame(data, configurationService.retrieve());
		configurationService.clearState();
		data = new TestConfig("b");
		doReturn(data).when(persistenceService).load();

		assertSame(data, configurationService.retrieve());
	}

	@Test
	public void update() {
		verifyNoMoreInteractions(eventSupport);
		TestConfig data2 = new TestConfig("b");
		configurationService.update(data2);
		verify(persistenceService).save(data2);
		verify(eventSupport).updated(data2);
		assertSame(data2, configurationService.retrieve());
		verifyNoMoreInteractions(eventSupport);
	}
}

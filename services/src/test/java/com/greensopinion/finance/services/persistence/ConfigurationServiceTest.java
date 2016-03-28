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

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;

import com.greensopinion.finance.services.domain.EntityEventSupport;
import com.greensopinion.finance.services.persistence.ConfigurationService;
import com.greensopinion.finance.services.persistence.PersistenceService;

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

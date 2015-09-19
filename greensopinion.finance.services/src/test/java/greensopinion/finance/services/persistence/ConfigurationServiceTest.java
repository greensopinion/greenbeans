package greensopinion.finance.services.persistence;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import greensopinion.finance.services.persistence.ConfigurationService;
import greensopinion.finance.services.persistence.PersistenceService;

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

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		data = new TestConfig("a");
		persistenceService = mock(PersistenceService.class);
		doReturn(data).when(persistenceService).load();

		configurationService = new ConfigurationService<TestConfig>(persistenceService);
	}

	@Test
	public void retrieve() {
		assertSame(data, configurationService.retrieve());
		assertSame(data, configurationService.retrieve());
	}

	@Test
	public void update() {
		TestConfig data2 = new TestConfig("b");
		configurationService.update(data2);
		verify(persistenceService).save(data2);
		assertSame(data2, configurationService.retrieve());
	}
}

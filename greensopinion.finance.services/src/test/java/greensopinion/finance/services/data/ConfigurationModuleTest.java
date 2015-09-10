package greensopinion.finance.services.data;

import static greensopinion.finance.services.InjectorAsserts.assertSingletonBinding;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ConfigurationModuleTest {
	@Test
	public void providesConfigurationService() {
		assertSingletonBinding(createInjector(), ConfigurationService.class);
	}

	@Test
	public void providesDataService() {
		assertSingletonBinding(createInjector(), PersistenceService.class);
	}

	@Test
	public void providesDataDirectoryLocator() {
		assertSingletonBinding(createInjector(), DataDirectoryLocator.class);
	}

	private Injector createInjector() {
		return Guice.createInjector(new ConfigurationModule());
	}
}

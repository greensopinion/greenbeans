package greensopinion.finance.services.data;

import static greensopinion.finance.services.InjectorAsserts.assertSingletonBinding;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

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

	private Injector createInjector() {
		return Guice.createInjector(new ConfigurationModule());
	}
}

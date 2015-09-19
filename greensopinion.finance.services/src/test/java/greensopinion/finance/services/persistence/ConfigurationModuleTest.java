package greensopinion.finance.services.persistence;

import static greensopinion.finance.services.InjectorAsserts.assertSingletonBinding;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import greensopinion.finance.services.domain.SettingsService;
import greensopinion.finance.services.domain.TransactionsService;
import greensopinion.finance.services.persistence.ConfigurationModule;
import greensopinion.finance.services.persistence.DataDirectoryLocator;
import greensopinion.finance.services.persistence.SettingsPersistenceService;
import greensopinion.finance.services.persistence.TransactionsPersistenceService;

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

package greensopinion.finance.services.data;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ConfigurationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SettingsPersistenceService.class).in(Scopes.SINGLETON);
		bind(SettingsService.class).in(Scopes.SINGLETON);

		bind(TransactionsPersistenceService.class).in(Scopes.SINGLETON);
		bind(TransactionsService.class).in(Scopes.SINGLETON);

		bind(DataDirectoryLocator.class).in(Scopes.SINGLETON);
	}
}

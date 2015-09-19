package greensopinion.finance.services.persistence;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import greensopinion.finance.services.domain.CategoriesService;
import greensopinion.finance.services.domain.SettingsService;
import greensopinion.finance.services.domain.TransactionsService;

public class ConfigurationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SettingsPersistenceService.class).in(Scopes.SINGLETON);
		bind(SettingsService.class).in(Scopes.SINGLETON);

		bind(TransactionsPersistenceService.class).in(Scopes.SINGLETON);
		bind(TransactionsService.class).in(Scopes.SINGLETON);

		bind(CategoriesPersistenceService.class).in(Scopes.SINGLETON);
		bind(CategoriesService.class).in(Scopes.SINGLETON);

		bind(DataDirectoryLocator.class).in(Scopes.SINGLETON);
	}
}

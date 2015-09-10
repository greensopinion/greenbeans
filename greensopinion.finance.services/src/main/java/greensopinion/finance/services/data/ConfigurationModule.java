package greensopinion.finance.services.data;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class ConfigurationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ConfigurationService.class).in(Scopes.SINGLETON);
		bind(PersistenceService.class).in(Scopes.SINGLETON);
		bind(DataDirectoryLocator.class).in(Scopes.SINGLETON);
	}
}

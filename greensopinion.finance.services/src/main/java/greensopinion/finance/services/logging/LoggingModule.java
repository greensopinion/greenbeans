package greensopinion.finance.services.logging;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class LoggingModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(LogConfigurator.class).in(Scopes.SINGLETON);
	}
}

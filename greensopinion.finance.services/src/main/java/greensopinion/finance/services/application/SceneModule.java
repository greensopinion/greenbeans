package greensopinion.finance.services.application;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import javafx.application.Application.Parameters;

class SceneModule extends AbstractModule {

	private final Parameters parameters;

	public SceneModule(Parameters parameters) {
		this.parameters = checkNotNull(parameters);
	}

	@Override
	protected void configure() {
		bind(Parameters.class).toInstance(parameters);
		bind(MainScene.class).in(Scopes.SINGLETON);
		bind(WebApplicationRegion.class).in(Scopes.SINGLETON);
		bind(ServiceLocator.class).in(Scopes.SINGLETON);
	}
}

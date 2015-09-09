package greensopinion.finance.services.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import greensopinion.finance.services.web.dispatch.Invoker;
import greensopinion.finance.services.web.dispatch.WebDispatch;

public class WebServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(WebDispatch.class).in(Scopes.SINGLETON);
		bind(GsonWebRenderer.class).in(Scopes.SINGLETON);
		bind(Invoker.class).in(Scopes.SINGLETON);
		bind(Gson.class).toInstance(new GsonBuilder().disableHtmlEscaping().create());

		bindWebServices();
	}

	private void bindWebServices() {
		bind(AboutWebService.class).in(Scopes.SINGLETON);
		bind(EncryptionSettingsWebService.class).in(Scopes.SINGLETON);
		bind(ImportFilesWebService.class).in(Scopes.SINGLETON);
	}
}

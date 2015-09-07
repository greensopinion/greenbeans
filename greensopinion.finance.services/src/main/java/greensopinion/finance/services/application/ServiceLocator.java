package greensopinion.finance.services.application;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import com.google.inject.Guice;
import com.google.inject.Injector;

import greensopinion.finance.services.bridge.WebInvoker;
import greensopinion.finance.services.data.ConfigurationModule;
import greensopinion.finance.services.encryption.EncryptionModule;
import greensopinion.finance.services.web.WebServiceModule;

public class ServiceLocator {
	@Inject
	private WebInvoker webInvoker;

	public ServiceLocator() {
		createInjector().injectMembers(this);
	}

	private Injector createInjector() {
		return Guice.createInjector(new EncryptionModule(), new ConfigurationModule(), new WebServiceModule());
	}

	public WebInvoker getWebInvoker() {
		return checkNotNull(webInvoker);
	}
}

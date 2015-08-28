package greensopinion.finance.services;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ServiceLocator {
	@Inject
	private AboutService aboutService;

	public ServiceLocator() {
		createInjector().injectMembers(this);
	}

	private Injector createInjector() {
		return Guice.createInjector();
	}

	public AboutService getAboutService() {
		return checkNotNull(aboutService);
	}
}

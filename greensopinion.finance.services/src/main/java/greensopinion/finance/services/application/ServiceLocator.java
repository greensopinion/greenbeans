package greensopinion.finance.services.application;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import greensopinion.finance.services.bridge.WebInvoker;

public class ServiceLocator {
	private final WebInvoker webInvoker;

	@Inject
	ServiceLocator(WebInvoker webInvoker) {
		this.webInvoker = checkNotNull(webInvoker);
	}

	public WebInvoker getWebInvoker() {
		return webInvoker;
	}
}

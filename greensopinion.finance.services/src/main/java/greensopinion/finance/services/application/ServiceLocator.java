/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
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

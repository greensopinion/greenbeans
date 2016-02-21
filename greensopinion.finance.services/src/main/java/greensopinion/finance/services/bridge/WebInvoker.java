/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.bridge;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import greensopinion.finance.services.web.dispatch.WebDispatch;
import greensopinion.finance.services.web.dispatch.WebRequest;
import greensopinion.finance.services.web.dispatch.WebResponse;

public class WebInvoker {

	private final WebDispatch dispatch;

	@Inject
	public WebInvoker(WebDispatch dispatch) {
		this.dispatch = checkNotNull(dispatch);
	}

	public WebResponse invoke(String httpMethod, String path, String entity) {
		return dispatch.dispatch(new WebRequest(httpMethod, path, entity));
	}
}

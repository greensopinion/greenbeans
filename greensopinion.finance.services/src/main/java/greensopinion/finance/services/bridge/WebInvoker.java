/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

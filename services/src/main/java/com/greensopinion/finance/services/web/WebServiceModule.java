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
package com.greensopinion.finance.services.web;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.greensopinion.finance.services.web.dispatch.Invoker;
import com.greensopinion.finance.services.web.dispatch.WebDispatch;

public class WebServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(WebDispatch.class).in(Scopes.SINGLETON);
		bind(GsonWebRenderer.class).in(Scopes.SINGLETON);
		bind(Invoker.class).in(Scopes.SINGLETON);
		bind(Gson.class).toProvider(WebGsonProvider.class).in(Scopes.SINGLETON);

		bindWebServices();
	}

	private void bindWebServices() {
		bind(AboutWebService.class).in(Scopes.SINGLETON);
		bind(CategoryWebService.class).in(Scopes.SINGLETON);
		bind(EncryptionSettingsWebService.class).in(Scopes.SINGLETON);
		bind(ImportFilesWebService.class).in(Scopes.SINGLETON);
		bind(ReportsWebService.class).in(Scopes.SINGLETON);
		bind(TransactionsWebService.class).in(Scopes.SINGLETON);
		bind(EulaWebService.class).in(Scopes.SINGLETON);
	}
}

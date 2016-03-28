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
package com.greensopinion.finance.services.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.greensopinion.finance.services.encryption.EncryptorProviderService;

class PersistenceGsonProvider {
	private final EncryptorProviderService encryptorProviderService;

	@Inject
	PersistenceGsonProvider(EncryptorProviderService encryptorProviderService) {
		this.encryptorProviderService = checkNotNull(encryptorProviderService);
	}

	public Gson get() {
		GsonBuilder gsonBuilder = builder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
		gsonBuilder.registerTypeAdapterFactory(TransactionsTypeAdapter.factory(encryptorProviderService));
		gsonBuilder.registerTypeAdapterFactory(CategoriesTypeAdapter.factory(encryptorProviderService));
		return gsonBuilder.create();
	}

	GsonBuilder builder() {
		return new GsonBuilder().disableHtmlEscaping();
	}
}

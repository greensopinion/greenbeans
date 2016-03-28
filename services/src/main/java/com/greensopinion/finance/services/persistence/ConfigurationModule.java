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

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.greensopinion.finance.services.domain.CategoriesService;
import com.greensopinion.finance.services.domain.EntityEventSupport;
import com.greensopinion.finance.services.domain.SettingsService;
import com.greensopinion.finance.services.domain.TransactionsService;

public class ConfigurationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EntityEventSupport.class).in(Scopes.SINGLETON);

		bind(SettingsPersistenceService.class).in(Scopes.SINGLETON);
		bind(SettingsService.class).in(Scopes.SINGLETON);

		bind(TransactionsPersistenceService.class).in(Scopes.SINGLETON);
		bind(TransactionsService.class).in(Scopes.SINGLETON);

		bind(CategoriesPersistenceService.class).in(Scopes.SINGLETON);
		bind(CategoriesService.class).in(Scopes.SINGLETON);

		bind(DataDirectoryLocator.class).in(Scopes.SINGLETON);
	}
}

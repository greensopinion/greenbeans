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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.Gson;
import com.greensopinion.finance.services.domain.Categories;
import com.greensopinion.finance.services.domain.Transactions;
import com.greensopinion.finance.services.persistence.CategoriesTypeAdapter;
import com.greensopinion.finance.services.persistence.PersistenceGsonProvider;
import com.greensopinion.finance.services.persistence.TransactionsTypeAdapter;

public class PersistenceGsonProviderTest {
	private final PersistenceGsonProvider provider = new PersistenceGsonProvider(MockEncryptorProviderService.create());

	@Test
	public void categoriesTypeAdapter() {
		Gson gson = provider.get();
		assertEquals(CategoriesTypeAdapter.class, gson.getAdapter(Categories.class).getClass());
	}

	@Test
	public void transactionsTypeAdapter() {
		Gson gson = provider.get();
		assertEquals(TransactionsTypeAdapter.class, gson.getAdapter(Transactions.class).getClass());
	}
}

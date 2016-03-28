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
package com.greensopinion.finance.services.encryption;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.inject.Injector;
import com.greensopinion.finance.services.AbstractIntegrationTest;
import com.greensopinion.finance.services.domain.Categories;
import com.greensopinion.finance.services.domain.CategoriesService;
import com.greensopinion.finance.services.domain.Category;
import com.greensopinion.finance.services.domain.Transactions;
import com.greensopinion.finance.services.domain.TransactionsService;
import com.greensopinion.finance.services.encryption.EncryptorService;
import com.greensopinion.finance.services.transaction.MockTransaction;

public class ChangeMasterPasswordIntegrationTest extends AbstractIntegrationTest {

	private final Transactions transactions = new Transactions(MockTransaction.create("2015-01-01", "a two", 123));
	private final Categories categories = new Categories(ImmutableList.of(new Category("a")));
	private TransactionsService transactionsService;
	private CategoriesService categoriesService;
	private EncryptorService encryptorService;

	@Before
	public void createData() {
		transactionsService = injector.getInstance(TransactionsService.class);
		categoriesService = injector.getInstance(CategoriesService.class);
		encryptorService = injector.getInstance(EncryptorService.class);

		transactionsService.update(transactions);
		categoriesService.update(categories);
	}

	@Test
	public void changeMasterPassword() {
		String newMasterPassword = "a new password " + System.currentTimeMillis();
		encryptorService.reconfigure(newMasterPassword);

		assertData(injector);
		Injector anotherInjector = createInjector();
		anotherInjector.getInstance(EncryptorService.class).initialize(newMasterPassword);
		assertData(anotherInjector);
	}

	private void assertData(Injector injector) {
		assertTransactions(injector);
		assertCategories(injector);
	}

	private void assertCategories(Injector injector) {
		CategoriesService categoriesService = injector.getInstance(CategoriesService.class);
		Categories retrieved = categoriesService.retrieve();
		assertEquals(categories.getCategories().size(), retrieved.getCategories().size());
		for (int x = 0; x < categories.getCategories().size(); ++x) {
			assertEquals(categories.getCategories().get(x).getName(), retrieved.getCategories().get(x).getName());
		}
	}

	private void assertTransactions(Injector injector) {
		TransactionsService transactionsService = injector.getInstance(TransactionsService.class);
		Transactions retrieved = transactionsService.retrieve();
		assertEquals(transactions.getTransactions(), retrieved.getTransactions());
	}
}

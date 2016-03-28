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
package com.greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.greensopinion.finance.services.domain.Categories;
import com.greensopinion.finance.services.domain.CategoriesService;
import com.greensopinion.finance.services.domain.CategorizerService;
import com.greensopinion.finance.services.domain.Category;
import com.greensopinion.finance.services.domain.MatchRule;
import com.greensopinion.finance.services.domain.Transaction;
import com.greensopinion.finance.services.transaction.MockTransaction;

public class CategorizerServiceTest {

	private CategorizerService service;

	@Before
	public void before() {
		CategoriesService categoriesService = mock(CategoriesService.class);
		Categories categories = createCategories();
		doReturn(categories).when(categoriesService).retrieve();
		service = new CategorizerService(categoriesService);
	}

	@Test
	public void categorize() {
		List<Transaction> transactions = ImmutableList.of(MockTransaction.create("2015-01-01", "a two", 123),
				MockTransaction.create("2015-01-02", "zzz", 123).withCategoryName("one"),
				MockTransaction.create("2015-01-03", "zzz", 123),
				MockTransaction.create("2015-01-02", "zzz", 123).withCategoryName("No Such Thing"));
		List<Transaction> categorized = service.categorize(transactions);
		assertEquals(transactions, categorized);
		assertCategory("two", categorized.get(0));
		assertCategory("one", categorized.get(1));
		assertEquals(null, categorized.get(2).getCategory());
		assertEquals(null, categorized.get(3).getCategory());
	}

	@Test
	public void categorizeNone() {
		List<Transaction> transactions = ImmutableList.of(MockTransaction.create("2015-01-01", "no", 123));
		List<Transaction> categorized = service.categorize(transactions);
		assertEquals(transactions, categorized);
		assertEquals(null, categorized.get(0).getCategory());
	}

	private void assertCategory(String categoryName, Transaction transaction) {
		assertNotNull(transaction);
		assertNotNull(transaction.getCategory());
		assertEquals(categoryName, transaction.getCategory().getName());
	}

	private Categories createCategories() {
		List<Category> categories = new ArrayList<>();
		categories.add(new Category("one").withMatchRule(MatchRule.withPattern("one")));
		categories.add(new Category("two").withMatchRule(MatchRule.withPattern("two")));
		return new Categories(categories);
	}
}

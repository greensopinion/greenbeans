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
package com.greensopinion.finance.services.web.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.greensopinion.finance.services.domain.Category;
import com.greensopinion.finance.services.domain.Transaction;
import com.greensopinion.finance.services.transaction.MockTransaction;
import com.greensopinion.finance.services.web.model.TransactionModel;

public class TransactionModelTest {

	@Test
	public void create() {
		Transaction transaction = MockTransaction.create("2015-01-03", "a desc", 1234);
		TransactionModel model = new TransactionModel(transaction);
		assertEquals(transaction.getId(), model.getId());
		assertEquals(transaction.getDate(), model.getDate());
		assertEquals(transaction.getDescription(), model.getDescription());
		assertEquals(transaction.getAmount(), model.getAmount());
		assertEquals(null, model.getCategoryName());
	}

	@Test
	public void createWithCategory() {
		Transaction transaction = MockTransaction.create("2015-01-03", "a desc", 1234)
				.withCategory(new Category("cat1"));
		TransactionModel model = new TransactionModel(transaction);
		assertEquals(transaction.getDate(), model.getDate());
		assertEquals(transaction.getDescription(), model.getDescription());
		assertEquals(transaction.getAmount(), model.getAmount());
		assertEquals("cat1", model.getCategoryName());
	}
}

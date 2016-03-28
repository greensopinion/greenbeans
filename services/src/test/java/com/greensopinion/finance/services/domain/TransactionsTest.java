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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.greensopinion.finance.services.domain.Transaction;
import com.greensopinion.finance.services.domain.Transactions;
import com.greensopinion.finance.services.transaction.MockTransaction;

public class TransactionsTest {

	@Test
	public void createWithArray() {
		Transaction t1 = MockTransaction.create("2015-01-02", "a", 123);
		Transaction t2 = MockTransaction.create("2015-01-02", "a", 123);
		Transactions transactions = new Transactions(t1, t2);
		assertEquals(ImmutableList.of(t1, t2), transactions.getTransactions());
	}

	@Test
	public void getTransactions() {
		assertNotNull(new Transactions().getTransactions());
	}

	@Test
	public void byId() {
		Transaction t1 = MockTransaction.create("2015-01-02", "a", 123);
		Transaction t2 = MockTransaction.create("2015-01-02", "a", 123);
		Transactions transactions = new Transactions(ImmutableList.of(t1, t2));
		assertSame(t1, transactions.byId(t1.getId()));
		assertSame(t2, transactions.byId(t2.getId()));
		assertEquals(null, transactions.byId("not here"));
	}

	@Test
	public void copyWithReplacement() {
		Transaction t1 = MockTransaction.create("2015-01-02", "a", 123);
		Transaction t2 = MockTransaction.create("2015-01-02", "a", 123);
		Transaction t3 = t1.withCategoryName("a cat");
		Transactions transactions = new Transactions(ImmutableList.of(t1, t2));

		Transactions copyWithReplacement = transactions.copyWithReplacement(t3);
		assertNotNull(copyWithReplacement);
		assertNotSame(transactions, copyWithReplacement);
		assertSame(t3, copyWithReplacement.byId(t3.getId()));
		assertSame(t1, transactions.byId(t1.getId()));
		assertEquals(ImmutableList.of(t1, t2), transactions.getTransactions());
		assertEquals(ImmutableList.of(t3, t2), copyWithReplacement.getTransactions());
	}
}

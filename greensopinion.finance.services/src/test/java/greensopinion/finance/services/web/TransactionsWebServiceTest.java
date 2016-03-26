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
package greensopinion.finance.services.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;
import greensopinion.finance.services.transaction.MockTransaction;
import greensopinion.finance.services.web.model.CategoryModel;

public class TransactionsWebServiceTest {

	private final TransactionsService transactionsService = mock(TransactionsService.class);
	private final TransactionsWebService webService = new TransactionsWebService(transactionsService);

	private final Transaction transaction1 = MockTransaction.create("2015-01-01", "abc", 123);
	private final Transaction transaction2 = MockTransaction.create("2015-01-02", "abc", 456);

	private final Transactions transactions = new Transactions(transaction1, transaction2);

	@Before
	public void before() {
		doReturn(transactions).when(transactionsService).retrieve();
	}

	@Test
	public void putCategory() {
		webService.putCategory(transaction1.getId(), new CategoryModel("a category"));

		ArgumentCaptor<Transactions> transactionsCaptor = ArgumentCaptor.forClass(Transactions.class);
		verify(transactionsService).update(transactionsCaptor.capture());
		Transactions updated = transactionsCaptor.getValue();

		Transaction transaction = updated.getTransactions().get(0);
		assertEquals(transaction1, transaction);
		assertEquals(transaction1.getId(), transaction.getId());
		assertEquals("a category", transaction.getCategoryName());
	}
}

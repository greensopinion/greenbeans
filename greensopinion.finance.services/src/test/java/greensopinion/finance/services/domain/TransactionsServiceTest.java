/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.persistence.TransactionsPersistenceService;
import greensopinion.finance.services.transaction.MockTransaction;

public class TransactionsServiceTest {
	private final CategorizerService categorizerService = mock(CategorizerService.class);
	private final TransactionsPersistenceService persistenceService = mock(TransactionsPersistenceService.class);
	private final EntityEventSupport eventSupport = new EntityEventSupport();
	private final TransactionsService service = new TransactionsService(persistenceService, categorizerService,
			eventSupport);
	private Transactions transactions;

	@Before
	public void before() {
		transactions = new Transactions(ImmutableList.of(MockTransaction.create("2015-01-01", "a desc", 123)));
		doReturn(transactions).when(persistenceService).load();

		Transactions categorizedTransactions = new Transactions(ImmutableList
				.of(MockTransaction.create("2015-01-01", "a desc", 123).withCategory(mock(Category.class))));
		doReturn(categorizedTransactions.getTransactions()).when(categorizerService)
				.categorize(transactions.getTransactions());
	}

	@Test
	public void retrieveAppliesCategories() {
		Transactions transactions = service.retrieve();
		assertCategorized(transactions);
	}

	@Test
	public void updateAppliesCategories() {
		service.update(transactions);
		ArgumentCaptor<Transactions> transactionsCaptor = ArgumentCaptor.forClass(Transactions.class);
		verify(persistenceService).save(transactionsCaptor.capture());
		assertCategorized(transactionsCaptor.getValue());
		verifyNoMoreInteractions(persistenceService);
	}

	@Test
	public void categoriesUpdated() {
		Transactions transactions = service.retrieve();
		eventSupport.updated(new Categories());

		Transactions transactions2 = new Transactions(
				ImmutableList.of(MockTransaction.create("2015-01-01", "a desc", 123)));
		doReturn(transactions2).when(persistenceService).load();

		Transactions transactions3 = service.retrieve();
		assertNotSame(transactions, transactions3);
		verify(persistenceService, times(2)).load();
	}

	private void assertCategorized(Transactions transactions) {
		assertFalse(transactions.getTransactions().isEmpty());
		for (Transaction transaction : transactions.getTransactions()) {
			assertNotNull(transaction.getCategory());
		}
	}
}

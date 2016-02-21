/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.AbstractIntegrationTest;

public class DomainIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void transactionServiceIsEmpty() {
		TransactionsService transactionsService = injector.getInstance(TransactionsService.class);
		Transactions transactions = transactionsService.retrieve();
		assertNotNull(transactions);
		assertEquals(ImmutableList.of(), transactions.getTransactions());
	}
}
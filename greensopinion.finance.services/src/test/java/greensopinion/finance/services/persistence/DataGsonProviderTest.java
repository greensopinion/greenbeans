/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;

import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.encryption.EncryptorProviderService;
import greensopinion.finance.services.persistence.PersistenceGsonProvider;
import greensopinion.finance.services.persistence.TransactionsTypeAdapter;

public class DataGsonProviderTest {

	@Test
	public void get() {
		Gson gson = new PersistenceGsonProvider(mock(EncryptorProviderService.class)).get();
		assertNotNull(gson);
		assertHasRfc3339DateFormat(gson);
		assertEquals(TransactionsTypeAdapter.class, gson.getAdapter(Transactions.class).getClass());
	}

	private void assertHasRfc3339DateFormat(Gson gson) {
		assertEquals("\"2015-09-08T22:45:31.031Z\"", gson.toJson(new Date(1441752331031L)));
	}
}

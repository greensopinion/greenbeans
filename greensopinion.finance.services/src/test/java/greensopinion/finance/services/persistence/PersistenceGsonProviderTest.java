/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.persistence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.Gson;

import greensopinion.finance.services.domain.Categories;
import greensopinion.finance.services.domain.Transactions;

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

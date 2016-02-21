/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.persistence;

import javax.inject.Inject;

import greensopinion.finance.services.domain.Transactions;

public class TransactionsPersistenceService extends PersistenceService<Transactions> {

	private static final String FILENAME = "transactions.json";

	@Inject
	TransactionsPersistenceService(PersistenceGsonProvider gsonProvider, DataDirectoryLocator dataDirectoryLocator) {
		super(gsonProvider, dataDirectoryLocator, Transactions.class);
	}

	@Override
	String getFilename() {
		return FILENAME;
	}

	@Override
	Transactions defaultInstance() {
		return new Transactions();
	}

}

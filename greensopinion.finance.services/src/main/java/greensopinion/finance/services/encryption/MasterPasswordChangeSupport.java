/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.encryption;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.logging.Logger;

import javax.inject.Inject;

import greensopinion.finance.services.domain.Categories;
import greensopinion.finance.services.domain.CategoriesService;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;

class MasterPasswordChangeSupport implements EncryptorListener {

	private final CategoriesService categoriesService;
	private final TransactionsService transactionsService;
	private Transactions transactions;
	private Categories categories;
	private final Logger logger;

	@Inject
	MasterPasswordChangeSupport(TransactionsService transactionsService, CategoriesService categoriesService,
			Logger logger) {
		this.transactionsService = checkNotNull(transactionsService);
		this.categoriesService = checkNotNull(categoriesService);
		this.logger = checkNotNull(logger);
	}

	@Override
	public void aboutToChangeEncryptor() {
		transactions = transactionsService.retrieve();
		categories = categoriesService.retrieve();
	}

	@Override
	public void encryptorChanged() {
		checkNotNull(transactions);
		checkNotNull(categories);
		transactionsService.update(transactions);
		transactions = null;
		categoriesService.update(categories);
		categories = null;
		logger.info("encrypted data with new master password");
	}
}

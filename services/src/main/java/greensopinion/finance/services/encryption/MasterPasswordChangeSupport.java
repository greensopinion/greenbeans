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

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
package greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import greensopinion.finance.services.persistence.ConfigurationService;
import greensopinion.finance.services.persistence.TransactionsPersistenceService;

public class TransactionsService extends ConfigurationService<Transactions> {

	private final CategorizerService categorizerService;

	@Inject
	TransactionsService(TransactionsPersistenceService persistenceService, CategorizerService categorizerService,
			EntityEventSupport eventSupport) {
		super(persistenceService, eventSupport);
		this.categorizerService = checkNotNull(categorizerService);
		eventSupport.addListener(new EntityListener<Categories>(Categories.class) {

			@Override
			public void updated(Categories entity) {
				clearState();
			}
		});
	}

	@Override
	protected Transactions load() {
		return applyCategories(super.load());
	}

	@Override
	public void update(Transactions value) {
		Transactions newValue = applyCategories(value);
		super.update(newValue);
	}

	private Transactions applyCategories(Transactions transactions) {
		return new Transactions(categorizerService.categorize(transactions.getTransactions()));
	}
}

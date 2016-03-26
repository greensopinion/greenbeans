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

import static com.google.common.base.Preconditions.checkNotNull;
import static greensopinion.finance.services.ValidationPreconditions.validate;
import static greensopinion.finance.services.ValidationPreconditions.validateNotNull;
import static greensopinion.finance.services.ValidationPreconditions.validateRequired;
import static java.text.MessageFormat.format;

import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;
import greensopinion.finance.services.web.model.CategoryModel;

@Path(TransactionsWebService.BASE_PATH)
public class TransactionsWebService {
	static final String BASE_PATH = "/transactions";

	private final TransactionsService transactionsService;

	@Inject
	TransactionsWebService(TransactionsService transactionsService) {
		this.transactionsService = checkNotNull(transactionsService);
	}

	@PUT
	@Path("{transactionId}/category")
	public void putCategory(@PathParam("transactionId") String transactionId, CategoryModel category) {
		validateRequired(transactionId, "Transaction id");
		validateNotNull(category, "Category must be provided.");
		validateRequired(category.getName(), "Category name");

		Transactions transactions = transactionsService.retrieve();
		Transaction transaction = transactions.byId(transactionId);
		validate(transaction != null, format("Transaction with id \"{0}\" not found.", transactionId));

		Transaction categorized = transaction.withCategoryName(category.getName());

		transactionsService.update(transactions.copyWithReplacement(categorized));
	}
}

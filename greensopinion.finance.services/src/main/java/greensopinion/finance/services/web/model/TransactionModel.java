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
package greensopinion.finance.services.web.model;

import java.util.Date;

import greensopinion.finance.services.domain.Category;
import greensopinion.finance.services.domain.Transaction;

public class TransactionModel {
	private final String id;
	private final Date date;
	private final long amount;
	private final String description;
	private final String categoryName;

	public TransactionModel(Transaction transaction) {
		id = transaction.getId();
		date = transaction.getDate();
		amount = transaction.getAmount();
		description = transaction.getDescription();
		Category category = transaction.getCategory();
		categoryName = category == null ? null : category.getName();
	}

	public String getId() {
		return id;
	}

	public long getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getDescription() {
		return description;
	}
}

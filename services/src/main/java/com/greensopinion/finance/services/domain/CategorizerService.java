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
package com.greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.ImmutableList;

public class CategorizerService {
	private final CategoriesService categoriesService;

	@Inject
	CategorizerService(CategoriesService categoriesService) {
		this.categoriesService = checkNotNull(categoriesService);
	}

	public List<Transaction> categorize(List<Transaction> transactions) {
		checkNotNull(transactions);
		ImmutableList.Builder<Transaction> builder = ImmutableList.builder();
		for (Transaction transaction : transactions) {
			builder.add(categorize(transaction));
		}
		return builder.build();
	}

	private Transaction categorize(Transaction transaction) {
		Categories categories = categoriesService.retrieve();
		for (Category category : categories.getCategories()) {
			if (category.matches(transaction)) {
				return transaction.withCategory(category);
			}
		}
		return transaction;
	}
}

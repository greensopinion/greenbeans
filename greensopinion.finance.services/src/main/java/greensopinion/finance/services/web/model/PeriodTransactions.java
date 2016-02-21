/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import greensopinion.finance.services.domain.Transaction;

public class PeriodTransactions {
	private final String name;
	private final List<TransactionModel> transactions;

	public PeriodTransactions(String name, List<Transaction> transactions) {
		this.name = checkNotNull(name);
		this.transactions = FluentIterable.from(transactions).transform(new Function<Transaction, TransactionModel>() {

			@Override
			public TransactionModel apply(Transaction transaction) {
				return new TransactionModel(transaction);
			}
		}).toList();
	}

	public String getName() {
		return name;
	}

	public List<TransactionModel> getTransactions() {
		return transactions;
	}
}

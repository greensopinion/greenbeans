/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class Transactions {
	private final List<Transaction> transactions;

	public Transactions() {
		transactions = ImmutableList.of();
	}

	public Transactions(List<Transaction> transactions) {
		this.transactions = ImmutableList.copyOf(transactions);
	}

	public Transactions(Transaction... transactions) {
		this(Arrays.asList(transactions));
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public Transaction byId(String id) {
		checkNotNull(id);
		for (Transaction transaction : transactions) {
			if (transaction.getId().equals(id)) {
				return transaction;
			}
		}
		return null;
	}

	public Transactions copyWithReplacement(Transaction transaction) {
		boolean found = false;
		ImmutableList.Builder<Transaction> builder = ImmutableList.<Transaction> builder();
		for (Transaction t : transactions) {
			if (t.getId().equals(transaction.getId())) {
				found = true;
				builder.add(transaction);
			} else {
				builder.add(t);
			}
		}
		checkArgument(found, "Transaction not present");
		return new Transactions(builder.build());
	}
}

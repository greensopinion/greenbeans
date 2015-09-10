package greensopinion.finance.services.data;

import java.util.List;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.transaction.Transaction;

public class Transactions {
	private final List<Transaction> transactions;

	public Transactions() {
		transactions = ImmutableList.of();
	}

	public Transactions(List<Transaction> transactions) {
		this.transactions = ImmutableList.copyOf(transactions);
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}
}

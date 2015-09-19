package greensopinion.finance.services.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import greensopinion.finance.services.transaction.Transaction;

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

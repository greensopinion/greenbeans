package greensopinion.finance.services.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Sets;

import greensopinion.finance.services.domain.Transaction;

public class TransactionNormalizer {
	/**
	 * Removes transaction pairs that are self-canceling.
	 */
	public List<Transaction> normalize(List<Transaction> transactions) {
		List<Transaction> normalizedTransactions = new ArrayList<>(transactions);

		ListMultimap<Long, Transaction> transactionByAbsoluteValue = ArrayListMultimap.create();
		for (Transaction transaction : transactions) {
			transactionByAbsoluteValue.put(Math.abs(transaction.getAmount()), transaction);
		}
		for (Long amount : transactionByAbsoluteValue.keySet()) {
			List<Transaction> list = transactionByAbsoluteValue.get(amount);
			Set<Transaction> canceledOut = Sets.newIdentityHashSet();
			for (Transaction transaction : list) {
				if (canceledOut.contains(transaction)) {
					continue;
				}
				for (Transaction transaction2 : list) {
					if (transaction.getAmount() == -transaction2.getAmount() && !canceledOut.contains(transaction2)) {
						canceledOut.add(transaction);
						canceledOut.add(transaction2);
						break;
					}
				}
			}
			normalizedTransactions.removeAll(canceledOut);
		}

		return normalizedTransactions;
	}
}

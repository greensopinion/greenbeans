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

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

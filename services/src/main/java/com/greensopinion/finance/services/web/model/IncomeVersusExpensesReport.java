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
package com.greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.greensopinion.finance.services.domain.Transaction;
import com.greensopinion.finance.services.transaction.TransactionNormalizer;

public class IncomeVersusExpensesReport extends Report {

	public static class Month {
		private final long id;
		private final String name;
		private final long incomeTotal;
		private final long expensesTotal;

		public Month(long id, String name, List<Transaction> transactions) {
			this.id = id;
			this.name = checkNotNull(name);
			checkNotNull(transactions);

			TransactionNormalizer transactionNormalizer = new TransactionNormalizer();
			long incomeTotal = 0L;
			long expensesTotal = 0L;
			for (Transaction transaction : transactionNormalizer.normalize(transactions)) {
				long amount = transaction.getAmount();
				if (amount <= 0) {
					expensesTotal += Math.abs(amount);
				} else {
					incomeTotal += amount;
				}
			}
			this.incomeTotal = incomeTotal;
			this.expensesTotal = expensesTotal;
		}

		public long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public long getIncomeTotal() {
			return incomeTotal;
		}

		public long getExpensesTotal() {
			return expensesTotal;
		}
	}

	private final List<Month> months = new ArrayList<>();

	public IncomeVersusExpensesReport() {
		super("Monthly Income vs Expenses");
	}

	public List<Month> getMonths() {
		return ImmutableList.copyOf(months);
	}

	public void addMonth(Month month) {
		months.add(month);
	}
}

/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.transaction.TransactionNormalizer;

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

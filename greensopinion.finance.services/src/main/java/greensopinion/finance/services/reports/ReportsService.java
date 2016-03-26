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
package greensopinion.finance.services.reports;

import static com.google.common.base.Preconditions.checkNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import com.google.common.base.Throwables;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import greensopinion.finance.services.domain.Category;
import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;
import greensopinion.finance.services.transaction.TransactionNormalizer;
import greensopinion.finance.services.web.model.CategorySummary;
import greensopinion.finance.services.web.model.ExpensesByCategoryReport;
import greensopinion.finance.services.web.model.IncomeVersusExpensesReport;
import greensopinion.finance.services.web.model.IncomeVersusExpensesReport.Month;
import greensopinion.finance.services.web.model.PeriodDetails;
import greensopinion.finance.services.web.model.PeriodTransactions;

public class ReportsService {
	private static final ThreadLocal<DateFormat> yearMonthFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMM");
		}
	};
	private static final ThreadLocal<DateFormat> readableYearMonthFormat = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("MMMMMMMMMMM yyyy");
		}
	};

	private final TransactionsService transactionsService;

	@Inject
	ReportsService(TransactionsService transactionsService) {
		this.transactionsService = checkNotNull(transactionsService);
	}

	public IncomeVersusExpensesReport incomeVersusExpenses() {
		IncomeVersusExpensesReport report = new IncomeVersusExpensesReport();

		Transactions transactions = transactionsService.retrieve();

		ListMultimap<Long, Transaction> transactionsByMonth = ArrayListMultimap.create();
		for (Transaction transaction : transactions.getTransactions()) {
			Long yearMonth = yearMonth(transaction.getDate());
			transactionsByMonth.put(yearMonth, transaction);
		}
		List<Long> sortedMonths = new ArrayList<>(transactionsByMonth.keySet());
		Collections.sort(sortedMonths);
		for (final Long yearMonth : sortedMonths) {
			String name = monthName(yearMonth);
			report.addMonth(new Month(yearMonth, name, transactionsByMonth.get(yearMonth)));
		}
		return report;
	}

	public ExpensesByCategoryReport expensesByCategory() {
		ExpensesByCategoryReport report = new ExpensesByCategoryReport();

		Set<Long> yearMonths = new HashSet<>();

		Transactions transactions = transactionsService.retrieve();
		for (Transaction transaction : transactions.getTransactions()) {
			Long yearMonth = yearMonth(transaction.getDate());
			yearMonths.add(yearMonth);
		}
		List<Long> sortedMonths = new ArrayList<>(yearMonths);
		Collections.sort(sortedMonths);
		for (final Long yearMonth : sortedMonths) {
			report.add(detailsForMonth(yearMonth));
		}

		return report;
	}

	public PeriodTransactions transactionsForMonth(long yearMonth) {
		return new PeriodTransactions(monthName(yearMonth), transactions(yearMonth));
	}

	public PeriodDetails detailsForMonth(long yearMonth) {
		List<Transaction> transactions = new TransactionNormalizer().normalize(transactions(yearMonth));
		Map<String, Long> amountByCategoryName = new HashMap<>();
		for (Transaction transaction : transactions) {
			Category category = transaction.getCategory();
			String categoryName = category == null ? "Uncategorized" : category.getName();
			Long amount = amountByCategoryName.get(categoryName);
			if (amount == null) {
				amount = 0L;
			}
			amountByCategoryName.put(categoryName, amount.longValue() + transaction.getAmount());
		}
		List<CategorySummary> categorySummaries = new ArrayList<>();
		for (Entry<String, Long> entry : amountByCategoryName.entrySet()) {
			categorySummaries.add(new CategorySummary(entry.getKey(), entry.getValue()));
		}
		Collections.sort(categorySummaries, new CategorySummaryAmountDescendingComparator());
		return new PeriodDetails(monthName(yearMonth), categorySummaries);
	}

	private List<Transaction> transactions(long yearMonth) {
		Transactions transactions = transactionsService.retrieve();
		List<Transaction> elements = new ArrayList<>();
		for (Transaction transaction : transactions.getTransactions()) {
			Long transactionYearMonth = yearMonth(transaction.getDate());
			if (transactionYearMonth.equals(yearMonth)) {
				elements.add(transaction);
			}
		}
		return elements;
	}

	private String monthName(final Long yearMonth) {
		try {
			return readableYearMonthFormat().format(yearMonthFormat().parse(yearMonth.toString()));
		} catch (ParseException e) {
			throw Throwables.propagate(e);
		}
	}

	private Long yearMonth(Date date) {
		return Long.parseLong(yearMonthFormat().format(date));
	}

	private DateFormat yearMonthFormat() {
		return yearMonthFormat.get();
	}

	private DateFormat readableYearMonthFormat() {
		return readableYearMonthFormat.get();
	}
}

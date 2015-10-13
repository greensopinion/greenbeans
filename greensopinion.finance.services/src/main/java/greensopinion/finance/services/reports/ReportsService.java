package greensopinion.finance.services.reports;

import static com.google.common.base.Preconditions.checkNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.base.Throwables;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ListMultimap;

import greensopinion.finance.services.domain.Category;
import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;
import greensopinion.finance.services.transaction.TransactionNormalizer;
import greensopinion.finance.services.web.model.IncomeVersusExpensesReport;
import greensopinion.finance.services.web.model.IncomeVersusExpensesReport.Month;
import greensopinion.finance.services.web.model.PeriodDetails;
import greensopinion.finance.services.web.model.PeriodTransactions;

public class ReportsService {
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
		List<PeriodDetails.CategorySummary> categorySummaries = new ArrayList<>();
		for (String name : FluentIterable.from(amountByCategoryName.keySet())
				.toSortedList(String.CASE_INSENSITIVE_ORDER)) {
			categorySummaries.add(new PeriodDetails.CategorySummary(name, amountByCategoryName.get(name)));
		}
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
		return new SimpleDateFormat("yyyyMM");
	}

	private DateFormat readableYearMonthFormat() {
		return new SimpleDateFormat("MMMMMMMMMMM yyyy");
	}

}

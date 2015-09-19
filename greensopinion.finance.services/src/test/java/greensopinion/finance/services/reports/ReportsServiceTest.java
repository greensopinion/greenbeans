package greensopinion.finance.services.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Throwables;

import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;
import greensopinion.finance.services.web.model.IncomeVersusExpensesReport;
import greensopinion.finance.services.web.model.PeriodTransactions;
import greensopinion.finance.services.web.model.TransactionModel;
import greensopinion.finance.services.web.model.IncomeVersusExpensesReport.Month;

public class ReportsServiceTest {

	private final TransactionsService transactionsService = mock(TransactionsService.class);
	private final ReportsService service = new ReportsService(transactionsService);
	private final Transactions transactions = createTransactions();

	@Before
	public void before() {
		doReturn(transactions).when(transactionsService).retrieve();
	}

	@Test
	public void incomeVersusExpenses() {
		IncomeVersusExpensesReport report = service.incomeVersusExpenses();
		List<Month> months = report.getMonths();
		assertNotNull(months);
		assertEquals(2, months.size());
		assertMonth("January 2015", 102300, 1500, months.get(0));
		assertMonth("February 2015", 0, 12345, months.get(1));
	}

	@Test
	public void transactionsForMonth() {
		PeriodTransactions transactionsForMonth = service.transactionsForMonth(201502);
		assertEquals("February 2015", transactionsForMonth.getName());
		assertEquals(1, transactionsForMonth.getTransactions().size());
		assertTransaction(createTransactions().getTransactions().get(4), transactionsForMonth.getTransactions().get(0));
	}

	private void assertTransaction(Transaction transaction, TransactionModel transactionModel) {
		assertEquals(transaction.getDate(), transactionModel.getDate());
		assertEquals(transaction.getAmount(), transactionModel.getAmount());
		assertEquals(transaction.getDescription(), transactionModel.getDescription());
	}

	private void assertMonth(String monthName, long incomeTotal, long expensesTotal, Month month) {
		assertEquals(monthName, month.getName());
		assertEquals(incomeTotal, month.getIncomeTotal());
		assertEquals(expensesTotal, month.getExpensesTotal());
	}

	private Transactions createTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction(date("2015-01-03"), "test1", 102300));
		transactions.add(new Transaction(date("2015-01-03"), "test2", -1500));
		transactions.add(new Transaction(date("2015-01-05"), "test3", -1504));
		transactions.add(new Transaction(date("2015-01-05"), "test4", 1504));
		transactions.add(new Transaction(date("2015-02-15"), "test5", -12345));
		return new Transactions(transactions);
	}

	private Date date(String date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw Throwables.propagate(e);
		}
	}
}

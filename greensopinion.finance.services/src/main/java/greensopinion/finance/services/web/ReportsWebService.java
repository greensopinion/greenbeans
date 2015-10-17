package greensopinion.finance.services.web;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import greensopinion.finance.services.reports.ReportsService;
import greensopinion.finance.services.web.model.ExpensesByCategoryReport;
import greensopinion.finance.services.web.model.IncomeVersusExpensesReport;
import greensopinion.finance.services.web.model.PeriodDetails;
import greensopinion.finance.services.web.model.PeriodTransactions;

@Path(ReportsWebService.BASE_PATH)
public class ReportsWebService {
	static final String BASE_PATH = "/reports";

	static final String PATH_INCOME_VS_EXPENSES = "income-vs-expenses";
	static final String PATH_EXPENSES_BY_CATEGORY = "expenses-by-category";
	static final String PATH_TRANSACTIONS_FOR_MONTH = "transactions/{yearMonth}";
	static final String PATH_DETAILS_FOR_MONTH = "details/{yearMonth}";

	private final ReportsService reportsService;

	@Inject
	ReportsWebService(ReportsService reportsService) {
		this.reportsService = checkNotNull(reportsService);
	}

	@Path(PATH_INCOME_VS_EXPENSES)
	@GET
	public IncomeVersusExpensesReport incomeVersusExpenses() {
		return reportsService.incomeVersusExpenses();
	}

	@Path(PATH_EXPENSES_BY_CATEGORY)
	@GET
	public ExpensesByCategoryReport expensesByCategory() {
		return reportsService.expensesByCategory();
	}

	@Path(PATH_TRANSACTIONS_FOR_MONTH)
	@GET
	public PeriodTransactions transactionsForMonth(@PathParam("yearMonth") long yearMonth) {
		return reportsService.transactionsForMonth(yearMonth);
	}

	@Path(PATH_DETAILS_FOR_MONTH)
	@GET
	public PeriodDetails detailsForMonth(@PathParam("yearMonth") long yearMonth) {
		return reportsService.detailsForMonth(yearMonth);
	}

}

package greensopinion.finance.services.web;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import greensopinion.finance.services.model.IncomeVersusExpensesReport;
import greensopinion.finance.services.reports.ReportsService;

@Path(ReportsWebService.BASE_PATH)
public class ReportsWebService {
	static final String BASE_PATH = "/reports";

	static final String PATH_INCOME_VS_EXPENSES = "income-vs-expenses";

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
}

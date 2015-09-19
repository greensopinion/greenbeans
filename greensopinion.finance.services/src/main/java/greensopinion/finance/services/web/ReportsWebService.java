package greensopinion.finance.services.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import greensopinion.finance.services.model.IncomeVersusExpensesReport;
import greensopinion.finance.services.model.IncomeVersusExpensesReport.Month;

@Path(ReportsWebService.BASE_PATH)
public class ReportsWebService {
	static final String BASE_PATH = "/reports";

	static final String PATH_INCOME_VS_EXPENSES = "income-vs-expenses";

	@Path(PATH_INCOME_VS_EXPENSES)
	@GET
	public IncomeVersusExpensesReport incomeVersusExpenses() {
		IncomeVersusExpensesReport report = new IncomeVersusExpensesReport();
		report.addMonth(new Month("2015-07", 1000000, 653543));
		report.addMonth(new Month("2015-08", 1000000, 540009));
		report.addMonth(new Month("2015-09", 1000000, 720014));
		report.addMonth(new Month("2015-10", 1000023, 944590));
		report.addMonth(new Month("2015-11", 1000000, 1234000));
		return report;
	}
}

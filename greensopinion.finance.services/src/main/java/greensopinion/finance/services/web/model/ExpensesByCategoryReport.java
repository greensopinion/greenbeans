package greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class ExpensesByCategoryReport extends Report {
	private final List<PeriodDetails> monthlyDetails = new ArrayList<>();

	public ExpensesByCategoryReport() {
		super("Monthly Expenses By Category");
	}

	public void add(PeriodDetails detailsForMonth) {
		monthlyDetails.add(checkNotNull(detailsForMonth));
	}

	public List<PeriodDetails> getMonthlyDetails() {
		return ImmutableList.copyOf(monthlyDetails);
	}
}

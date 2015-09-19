package greensopinion.finance.services.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class IncomeVersusExpensesReport extends Report {

	public static class Month {
		private final String name;
		private final long incomeTotal;
		private final long expensesTotal;

		public Month(String name, long incomeTotal, long expensesTotal) {
			this.name = name;
			this.incomeTotal = incomeTotal;
			this.expensesTotal = expensesTotal;
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

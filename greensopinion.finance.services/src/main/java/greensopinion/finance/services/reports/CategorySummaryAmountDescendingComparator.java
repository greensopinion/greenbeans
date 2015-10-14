package greensopinion.finance.services.reports;

import java.util.Comparator;

import greensopinion.finance.services.web.model.PeriodDetails.CategorySummary;

class CategorySummaryAmountDescendingComparator implements Comparator<CategorySummary> {

	@Override
	public int compare(CategorySummary o1, CategorySummary o2) {
		int i = Long.valueOf(o1.getAmount()).compareTo(o2.getAmount());
		if (i == 0) {
			i = o1.getName().compareToIgnoreCase(o2.getName());
		}
		return i;
	}
}

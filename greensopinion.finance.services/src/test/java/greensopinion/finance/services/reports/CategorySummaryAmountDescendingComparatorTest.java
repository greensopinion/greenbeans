package greensopinion.finance.services.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import greensopinion.finance.services.web.model.CategorySummary;

public class CategorySummaryAmountDescendingComparatorTest {
	private final CategorySummaryAmountDescendingComparator comparator = new CategorySummaryAmountDescendingComparator();

	@Test
	public void compareTo() {
		CategorySummary summary1 = new CategorySummary("one", -1);
		CategorySummary summary2 = new CategorySummary("two", -2);
		assertEquals(0, comparator.compare(summary1, summary1));
		assertEquals(0, comparator.compare(summary2, summary2));
		assertEquals(1, comparator.compare(summary1, summary2));
		assertEquals(-1, comparator.compare(summary2, summary1));
	}
}

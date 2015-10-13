package greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class PeriodDetails {
	public static class CategorySummary {
		private final String name;
		private final long amount;

		public CategorySummary(String categoryName, long amount) {
			this.name = categoryName;
			this.amount = amount;
		}

		public String getName() {
			return name;
		}

		public long getAmount() {
			return amount;
		}
	}

	private final String name;
	private final List<CategorySummary> categories;

	public PeriodDetails(String name, List<CategorySummary> categories) {
		this.name = checkNotNull(name);
		this.categories = ImmutableList.copyOf(categories);
	}

	public String getName() {
		return name;
	}

	public List<CategorySummary> getCategories() {
		return categories;
	}
}

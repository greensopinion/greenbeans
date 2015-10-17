package greensopinion.finance.services.web.model;

public class CategorySummary {
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
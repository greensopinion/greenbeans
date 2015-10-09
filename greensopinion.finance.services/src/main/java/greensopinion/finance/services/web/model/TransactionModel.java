package greensopinion.finance.services.web.model;

import java.util.Date;

import greensopinion.finance.services.domain.Category;
import greensopinion.finance.services.domain.Transaction;

public class TransactionModel {
	private final Date date;
	private final long amount;
	private final String description;
	private final String categoryName;

	public TransactionModel(Transaction transaction) {
		date = transaction.getDate();
		amount = transaction.getAmount();
		description = transaction.getDescription();
		Category category = transaction.getCategory();
		categoryName = category == null ? null : category.getName();
	}

	public long getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getDescription() {
		return description;
	}
}

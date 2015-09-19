package greensopinion.finance.services.web.model;

import java.util.Date;

import greensopinion.finance.services.domain.Transaction;

public class TransactionModel {
	private final Date date;
	private final long amount;
	private final String description;

	public TransactionModel(Transaction transaction) {
		date = transaction.getDate();
		amount = transaction.getAmount();
		description = transaction.getDescription();
	}
	public long getAmount() {
		return amount;
	}
	public Date getDate() {
		return date;
	}
	public String getDescription() {
		return description;
	}
}

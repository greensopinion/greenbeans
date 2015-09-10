package greensopinion.finance.services.transaction;

import static com.google.common.base.Preconditions.checkNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import com.google.common.base.MoreObjects;

public class Transaction implements Comparable<Transaction> {
	private final Date date;
	private final String description;
	private final long amount;

	public Transaction(Date date, String description, long amount) {
		this.date = checkNotNull(date, "Must provide a date");
		this.description = checkNotNull(description, "Must provide a description");
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public long getAmount() {
		return amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, description, amount);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Transaction)) {
			return false;
		}
		Transaction other = (Transaction) obj;
		return amount == other.getAmount() && Objects.equals(getDate(), other.getDate())
				&& Objects.equals(getDescription(), other.getDescription());
	}

	@Override
	public int compareTo(Transaction o) {
		if (o == this) {
			return 0;
		}
		int c = date.compareTo(o.date);
		if (c == 0) {
			c = Long.valueOf(amount).compareTo(o.amount);
		}
		return c;
	}

	@Override
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return MoreObjects.toStringHelper(Transaction.class).add("date", dateFormat.format(date))//
				.add("amount", amount)//
				.add("description", description).toString();
	}
}

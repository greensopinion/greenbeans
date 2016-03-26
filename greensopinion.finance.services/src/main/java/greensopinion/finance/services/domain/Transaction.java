/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

import com.google.common.base.MoreObjects;

public class Transaction implements Comparable<Transaction> {
	private final String id;
	private final Date date;
	private final String description;
	private final long amount;
	private final String accountNumber;
	private final String categoryName;

	private transient final Category category;

	public Transaction(Date date, String description, long amount, Category category, String accountNumber) {
		this(UUID.randomUUID().toString(), date, description, amount, null, category, accountNumber);
	}

	private Transaction(String id, Date date, String description, long amount, String categoryName, Category category,
			String accountNumber) {
		this.id = checkNotNull(id);
		this.date = checkNotNull(date, "Must provide a date");
		this.description = checkNotNull(description, "Must provide a description");
		this.amount = amount;
		this.accountNumber = accountNumber;
		this.categoryName = categoryName;
		this.category = category;
	}

	public String getId() {
		return id;
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

	public String getAccountNumber() {
		return accountNumber;
	}

	public Category getCategory() {
		return category;
	}

	/**
	 * The name of the category that applies to this one transaction.
	 */
	public String getCategoryName() {
		return categoryName;
	}

	public Transaction withCategory(Category category) {
		return new Transaction(id, date, description, amount, categoryName, category, accountNumber);
	}

	public Transaction withCategoryName(String categoryName) {
		return new Transaction(id, date, description, amount, categoryName, category, accountNumber);
	}

	public Transaction withId(String id) {
		return new Transaction(id, date, description, amount, categoryName, category, accountNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, description.toLowerCase(), amount, accountNumber);
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
				&& getDescription().equalsIgnoreCase(other.getDescription())
				&& Objects.equals(accountNumber, other.getAccountNumber());
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
				.add("description", description).add("accountNumber", accountNumber).toString();
	}
}

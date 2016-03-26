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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.Date;

import org.junit.Test;

public class TransactionTest {
	@Test
	public void create() {
		Date date = new Date(1441842160000L);
		Transaction transaction = new Transaction(date, "a desc", 1003, null, null);
		assertEquals(date, transaction.getDate());
		assertEquals("a desc", transaction.getDescription());
		assertEquals(1003, transaction.getAmount());
		assertEquals(null, transaction.getAccountNumber());
		assertEquals(null, transaction.getCategory());
		assertEquals("Transaction{date=2015-09-09, amount=1003, description=a desc, accountNumber=null}",
				transaction.toString());

		assertEquals("123", new Transaction(date, "a desc", 1003, null, "123").getAccountNumber());
		Category category = new Category("category name");
		assertEquals(category, new Transaction(date, "a desc", 123, category, null).getCategory());
	}

	@Test
	public void compare() {
		Date date1 = new Date(1441842160000L);
		Date date2 = new Date(1441850000000L);
		Transaction transaction1 = new Transaction(date1, "a desc", 1003, null, "123");
		Transaction transaction2 = new Transaction(date2, "a desc", 1003, null, "456");
		Transaction transaction3 = new Transaction(date1, "a desc", 5000, null, null);
		assertOrder(transaction1, transaction2);
		assertOrder(transaction1, transaction3);
		assertOrder(transaction3, transaction2);
	}

	@Test
	public void equals() {
		Date date1 = new Date(1441842160000L);
		Date date2 = new Date(1441850000000L);
		Transaction transaction1 = new Transaction(date1, "a desc", 1003, null, null);
		Transaction transaction2 = new Transaction(date1, "a desc", 1003, null, "123");
		Transaction transaction3 = transaction1.withCategoryName("some category");

		assertTransactionEqualsCaseInsensitive(transaction1);
		assertTransactionEqualsCaseInsensitive(transaction2);
		assertTransactionEqualsCaseInsensitive(transaction3);

		assertTransactionEquals(transaction1, transaction1);
		assertTransactionEquals(transaction1, transaction3);
		assertTransactionEquals(transaction1, new Transaction(date1, "a desc", 1003, null, null));
		assertTransactionEquals(transaction2, new Transaction(date1, "a desc", 1003, null, "123"));

		assertTransactionNotEquals(transaction1, new Object());
		assertTransactionNotEquals(transaction1, null);
		assertTransactionNotEquals(transaction1, transaction2);
		assertTransactionNotEquals(transaction1, new Transaction(date1, "a desc", 1004, null, null));
		assertTransactionNotEquals(transaction1, new Transaction(date1, "a desc2", 1003, null, null));
		assertTransactionNotEquals(transaction1, new Transaction(date2, "a desc", 1003, null, null));
	}

	@Test
	public void withCategory() {
		Category category = new Category("cat1");
		Date date1 = new Date(1441842160000L);
		Transaction transaction1 = new Transaction(date1, "a desc", 1003, null, "acct1");
		Transaction transaction2 = transaction1.withCategory(category);

		assertTransactionEquals(transaction1, transaction2);
		assertIdsEqual(transaction1, transaction2);
		assertNotSame(transaction1, transaction2);
		assertEquals(category, transaction2.getCategory());
		assertEquals(null, transaction1.getCategory());
	}

	@Test
	public void withCategoryName() {
		Transaction transaction1 = new Transaction(new Date(), "a desc", 1003, null, "acct1");
		Transaction transaction2 = transaction1.withCategoryName("A Category");
		assertNotNull(transaction2);
		assertNotSame(transaction1, transaction2);
		assertIdsEqual(transaction1, transaction2);
		assertEquals(null, transaction1.getCategoryName());
		assertEquals("A Category", transaction2.getCategoryName());
	}

	@Test
	public void withId() {
		Transaction transaction1 = new Transaction(new Date(), "a desc", 1003, null, "acct1");
		Transaction transaction2 = transaction1.withId("test12345");
		assertNotNull(transaction2);
		assertNotSame(transaction1, transaction2);
		assertEquals("test12345", transaction2.getId());
		assertEquals(transaction1, transaction2);
	}

	private void assertIdsEqual(Transaction transaction1, Transaction transaction2) {
		assertEquals(transaction1.getId(), transaction2.getId());
	}

	private void assertTransactionNotEquals(Transaction t0, Object o1) {
		assertEquals(false, t0.equals(o1));
		if (o1 != null) {
			assertEquals(false, o1.equals(t0));
		}
	}

	private void assertTransactionEquals(Transaction t0, Transaction t1) {
		assertEquals(t0.hashCode(), t1.hashCode());
		assertEquals(t0, t1);
		assertEquals(t1, t0);
	}

	private void assertTransactionEqualsCaseInsensitive(Transaction t) {
		Transaction t1 = new Transaction(t.getDate(), t.getDescription().toUpperCase(), t.getAmount(), t.getCategory(),
				t.getAccountNumber());
		Transaction t2 = new Transaction(t.getDate(), t.getDescription().toLowerCase(), t.getAmount(), t.getCategory(),
				t.getAccountNumber());

		assertEquals(t, t1);
		assertEquals(t, t2);
	}

	private void assertOrder(Transaction t0, Transaction t1) {
		assertEquals(0, t0.compareTo(t0));
		assertEquals(0, t1.compareTo(t1));
		assertEquals(-1, t0.compareTo(t1));
		assertEquals(1, t1.compareTo(t0));
	}
}

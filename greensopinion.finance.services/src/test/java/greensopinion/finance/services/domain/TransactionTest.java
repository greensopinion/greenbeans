package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class TransactionTest {
	@Test
	public void create() {
		Date date = new Date(1441842160000L);
		Transaction transaction = new Transaction(date, "a desc", 1003, null);
		assertEquals(date, transaction.getDate());
		assertEquals("a desc", transaction.getDescription());
		assertEquals(1003, transaction.getAmount());
		assertEquals(null, transaction.getAccountNumber());
		assertEquals("Transaction{date=2015-09-09, amount=1003, description=a desc, accountNumber=null}",
				transaction.toString());

		assertEquals("123", new Transaction(date, "a desc", 1003, "123").getAccountNumber());
	}

	@Test
	public void compare() {
		Date date1 = new Date(1441842160000L);
		Date date2 = new Date(1441850000000L);
		Transaction transaction1 = new Transaction(date1, "a desc", 1003, "123");
		Transaction transaction2 = new Transaction(date2, "a desc", 1003, "456");
		Transaction transaction3 = new Transaction(date1, "a desc", 5000, null);
		assertOrder(transaction1, transaction2);
		assertOrder(transaction1, transaction3);
		assertOrder(transaction3, transaction2);
	}

	@Test
	public void equals() {
		Date date1 = new Date(1441842160000L);
		Date date2 = new Date(1441850000000L);
		Transaction transaction1 = new Transaction(date1, "a desc", 1003, null);
		Transaction transaction2 = new Transaction(date1, "a desc", 1003, "123");

		assertTransactionEquals(transaction1, transaction1);
		assertTransactionEquals(transaction1, new Transaction(date1, "a desc", 1003, null));
		assertTransactionEquals(transaction2, new Transaction(date1, "a desc", 1003, "123"));

		assertTransactionNotEquals(transaction1, new Object());
		assertTransactionNotEquals(transaction1, null);
		assertTransactionNotEquals(transaction1, transaction2);
		assertTransactionNotEquals(transaction1, new Transaction(date1, "a desc", 1004, null));
		assertTransactionNotEquals(transaction1, new Transaction(date1, "a desc2", 1003, null));
		assertTransactionNotEquals(transaction1, new Transaction(date2, "a desc", 1003, null));
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

	private void assertOrder(Transaction t0, Transaction t1) {
		assertEquals(0, t0.compareTo(t0));
		assertEquals(0, t1.compareTo(t1));
		assertEquals(-1, t0.compareTo(t1));
		assertEquals(1, t1.compareTo(t0));
	}
}

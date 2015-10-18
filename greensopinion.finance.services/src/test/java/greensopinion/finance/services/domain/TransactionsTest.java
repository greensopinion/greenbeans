package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.transaction.MockTransaction;

public class TransactionsTest {

	@Test
	public void createWithArray() {
		Transaction t1 = MockTransaction.create("2015-01-02", "a", 123);
		Transaction t2 = MockTransaction.create("2015-01-02", "a", 123);
		Transactions transactions = new Transactions(t1, t2);
		assertEquals(ImmutableList.of(t1, t2), transactions.getTransactions());
	}

	@Test
	public void getTransactions() {
		assertNotNull(new Transactions().getTransactions());
	}

	@Test
	public void byId() {
		Transaction t1 = MockTransaction.create("2015-01-02", "a", 123);
		Transaction t2 = MockTransaction.create("2015-01-02", "a", 123);
		Transactions transactions = new Transactions(ImmutableList.of(t1, t2));
		assertSame(t1, transactions.byId(t1.getId()));
		assertSame(t2, transactions.byId(t2.getId()));
		assertEquals(null, transactions.byId("not here"));
	}

	@Test
	public void copyWithReplacement() {
		Transaction t1 = MockTransaction.create("2015-01-02", "a", 123);
		Transaction t2 = MockTransaction.create("2015-01-02", "a", 123);
		Transaction t3 = t1.withCategoryName("a cat");
		Transactions transactions = new Transactions(ImmutableList.of(t1, t2));

		Transactions copyWithReplacement = transactions.copyWithReplacement(t3);
		assertNotNull(copyWithReplacement);
		assertNotSame(transactions, copyWithReplacement);
		assertSame(t3, copyWithReplacement.byId(t3.getId()));
		assertSame(t1, transactions.byId(t1.getId()));
		assertEquals(ImmutableList.of(t1, t2), transactions.getTransactions());
		assertEquals(ImmutableList.of(t3, t2), copyWithReplacement.getTransactions());
	}
}

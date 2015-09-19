package greensopinion.finance.services.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import greensopinion.finance.services.domain.Transactions;

public class TransactionsTest {

	@Test
	public void getTransactions() {
		assertNotNull(new Transactions().getTransactions());
	}
}

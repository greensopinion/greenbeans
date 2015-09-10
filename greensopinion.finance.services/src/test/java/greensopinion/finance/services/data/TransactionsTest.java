package greensopinion.finance.services.data;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TransactionsTest {

	@Test
	public void getTransactions() {
		assertNotNull(new Transactions().getTransactions());
	}
}

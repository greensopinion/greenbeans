package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.AbstractIntegrationTest;

public class DomainIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void transactionServiceIsEmpty() {
		TransactionsService transactionsService = injector.getInstance(TransactionsService.class);
		Transactions transactions = transactionsService.retrieve();
		assertNotNull(transactions);
		assertEquals(ImmutableList.of(), transactions.getTransactions());
	}
}
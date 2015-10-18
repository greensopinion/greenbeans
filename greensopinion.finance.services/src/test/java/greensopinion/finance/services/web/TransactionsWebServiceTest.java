package greensopinion.finance.services.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;
import greensopinion.finance.services.transaction.MockTransaction;
import greensopinion.finance.services.web.model.CategoryModel;

public class TransactionsWebServiceTest {

	private final TransactionsService transactionsService = mock(TransactionsService.class);
	private final TransactionsWebService webService = new TransactionsWebService(transactionsService);

	private final Transaction transaction1 = MockTransaction.create("2015-01-01", "abc", 123);
	private final Transaction transaction2 = MockTransaction.create("2015-01-02", "abc", 456);

	private final Transactions transactions = new Transactions(transaction1, transaction2);

	@Before
	public void before() {
		doReturn(transactions).when(transactionsService).retrieve();
	}

	@Test
	public void putCategory() {
		webService.putCategory(transaction1.getId(), new CategoryModel("a category"));

		ArgumentCaptor<Transactions> transactionsCaptor = ArgumentCaptor.forClass(Transactions.class);
		verify(transactionsService).update(transactionsCaptor.capture());
		Transactions updated = transactionsCaptor.getValue();

		Transaction transaction = updated.getTransactions().get(0);
		assertEquals(transaction1, transaction);
		assertEquals(transaction1.getId(), transaction.getId());
		assertEquals("a category", transaction.getCategoryName());
	}
}

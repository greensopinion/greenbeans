package greensopinion.finance.services.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.persistence.TransactionsPersistenceService;
import greensopinion.finance.services.transaction.MockTransaction;

public class TransactionsServiceTest {
	private final CategorizerService categorizerService = mock(CategorizerService.class);
	private final TransactionsPersistenceService persistenceService = mock(TransactionsPersistenceService.class);
	private final TransactionsService service = new TransactionsService(persistenceService, categorizerService);
	private Transactions transactions;

	@Before
	public void before() {
		transactions = new Transactions(ImmutableList.of(MockTransaction.create("2015-01-01", "a desc", 123)));
		doReturn(transactions).when(persistenceService).load();

		Transactions categorizedTransactions = new Transactions(ImmutableList
				.of(MockTransaction.create("2015-01-01", "a desc", 123).withCategory(mock(Category.class))));
		doReturn(categorizedTransactions.getTransactions()).when(categorizerService)
				.categorize(transactions.getTransactions());
	}

	@Test
	public void retrieveAppliesCategories() {
		Transactions transactions = service.retrieve();
		assertCategorized(transactions);
	}

	@Test
	public void updateAppliesCategories() {
		service.update(transactions);
		ArgumentCaptor<Transactions> transactionsCaptor = ArgumentCaptor.forClass(Transactions.class);
		verify(persistenceService).save(transactionsCaptor.capture());
		assertCategorized(transactionsCaptor.getValue());
		verifyNoMoreInteractions(persistenceService);
	}

	private void assertCategorized(Transactions transactions) {
		assertFalse(transactions.getTransactions().isEmpty());
		for (Transaction transaction : transactions.getTransactions()) {
			assertNotNull(transaction.getCategory());
		}
	}
}

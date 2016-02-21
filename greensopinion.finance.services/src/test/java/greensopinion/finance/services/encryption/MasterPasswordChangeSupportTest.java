package greensopinion.finance.services.encryption;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import greensopinion.finance.services.domain.Categories;
import greensopinion.finance.services.domain.CategoriesService;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;

public class MasterPasswordChangeSupportTest {

	private final TransactionsService transactionsService = mock(TransactionsService.class);
	private final CategoriesService categoriesService = mock(CategoriesService.class);
	private final MasterPasswordChangeSupport support = new MasterPasswordChangeSupport(transactionsService,
			categoriesService);
	private final Transactions transactions = mock(Transactions.class);
	private final Categories categories = mock(Categories.class);

	@Before
	public void before() {
		doReturn(transactions).when(transactionsService).retrieve();
		doReturn(categories).when(categoriesService).retrieve();
	}

	@Test
	public void support() {
		verifyZeroInteractions(transactionsService, categoriesService);
		support.aboutToChangeEncryptor();

		verify(transactionsService).retrieve();
		verify(categoriesService).retrieve();
		verifyNoMoreInteractions(transactionsService, categoriesService);

		support.encryptorChanged();

		InOrder inOrder = inOrder(transactionsService, categoriesService);
		inOrder.verify(transactionsService).update(transactions);
		inOrder.verify(categoriesService).update(categories);
	}
}

package greensopinion.finance.services.encryption;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.inject.Injector;

import greensopinion.finance.services.AbstractIntegrationTest;
import greensopinion.finance.services.domain.Categories;
import greensopinion.finance.services.domain.CategoriesService;
import greensopinion.finance.services.domain.Category;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;
import greensopinion.finance.services.transaction.MockTransaction;

public class ChangeMasterPasswordIntegrationTest extends AbstractIntegrationTest {

	private final Transactions transactions = new Transactions(MockTransaction.create("2015-01-01", "a two", 123));
	private final Categories categories = new Categories(ImmutableList.of(new Category("a")));
	private TransactionsService transactionsService;
	private CategoriesService categoriesService;
	private EncryptorService encryptorService;

	@Before
	public void createData() {
		transactionsService = injector.getInstance(TransactionsService.class);
		categoriesService = injector.getInstance(CategoriesService.class);
		encryptorService = injector.getInstance(EncryptorService.class);

		transactionsService.update(transactions);
		categoriesService.update(categories);
	}

	@Test
	public void changeMasterPassword() {
		String newMasterPassword = "a new password " + System.currentTimeMillis();
		encryptorService.reconfigure(newMasterPassword);

		assertData(injector);
		Injector anotherInjector = createInjector();
		anotherInjector.getInstance(EncryptorService.class).initialize(newMasterPassword);
		assertData(anotherInjector);
	}

	private void assertData(Injector injector) {
		assertTransactions(injector);
		assertCategories(injector);
	}

	private void assertCategories(Injector injector) {
		CategoriesService categoriesService = injector.getInstance(CategoriesService.class);
		Categories retrieved = categoriesService.retrieve();
		assertEquals(categories.getCategories().size(), retrieved.getCategories().size());
		for (int x = 0; x < categories.getCategories().size(); ++x) {
			assertEquals(categories.getCategories().get(x).getName(), retrieved.getCategories().get(x).getName());
		}
	}

	private void assertTransactions(Injector injector) {
		TransactionsService transactionsService = injector.getInstance(TransactionsService.class);
		Transactions retrieved = transactionsService.retrieve();
		assertEquals(transactions.getTransactions(), retrieved.getTransactions());
	}
}

package greensopinion.finance.services.web.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import greensopinion.finance.services.domain.Category;
import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.transaction.MockTransaction;

public class TransactionModelTest {

	@Test
	public void create() {
		Transaction transaction = MockTransaction.create("2015-01-03", "a desc", 1234);
		TransactionModel model = new TransactionModel(transaction);
		assertEquals(transaction.getDate(), model.getDate());
		assertEquals(transaction.getDescription(), model.getDescription());
		assertEquals(transaction.getAmount(), model.getAmount());
		assertEquals(null, model.getCategoryName());
	}

	@Test
	public void createWithCategory() {
		Transaction transaction = MockTransaction.create("2015-01-03", "a desc", 1234)
				.withCategory(new Category("cat1"));
		TransactionModel model = new TransactionModel(transaction);
		assertEquals(transaction.getDate(), model.getDate());
		assertEquals(transaction.getDescription(), model.getDescription());
		assertEquals(transaction.getAmount(), model.getAmount());
		assertEquals("cat1", model.getCategoryName());
	}
}

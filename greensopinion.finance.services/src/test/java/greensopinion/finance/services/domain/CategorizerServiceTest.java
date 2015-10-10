package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.transaction.MockTransaction;

public class CategorizerServiceTest {

	private CategorizerService service;

	@Before
	public void before() {
		CategoriesService categoriesService = mock(CategoriesService.class);
		Categories categories = createCategories();
		doReturn(categories).when(categoriesService).retrieve();
		service = new CategorizerService(categoriesService);
	}

	@Test
	public void categorize() {
		List<Transaction> transactions = ImmutableList.of(MockTransaction.create("2015-01-01", "a two", 123));
		List<Transaction> categorized = service.categorize(transactions);
		assertEquals(transactions, categorized);
		assertCategory("two", categorized.get(0));
	}

	@Test
	public void categorizeNone() {
		List<Transaction> transactions = ImmutableList.of(MockTransaction.create("2015-01-01", "no", 123));
		List<Transaction> categorized = service.categorize(transactions);
		assertEquals(transactions, categorized);
		assertEquals(null, categorized.get(0).getCategory());
	}

	private void assertCategory(String categoryName, Transaction transaction) {
		assertNotNull(transaction);
		assertNotNull(transaction.getCategory());
		assertEquals(categoryName, transaction.getCategory().getName());
	}

	private Categories createCategories() {
		List<Category> categories = new ArrayList<>();
		categories.add(new Category("one").withMatchRule(MatchRule.withPattern("one")));
		categories.add(new Category("two").withMatchRule(MatchRule.withPattern("two")));
		return new Categories(categories);
	}
}

package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class CategoriesTest {

	@Test
	public void create() {
		Categories categories = new Categories();
		assertEquals(ImmutableList.of(), categories.getCategories());
	}

	@Test
	public void createWithValues() {
		Category category1 = new Category("a");
		Category category2 = new Category("b");
		Categories categories = new Categories(ImmutableList.of(category1, category2));
		assertEquals(ImmutableList.of(category1, category2), categories.getCategories());
	}
}

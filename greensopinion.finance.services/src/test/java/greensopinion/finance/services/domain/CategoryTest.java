package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CategoryTest {

	@Test
	public void create() {
		Category category = new Category("a name");
		assertEquals("a name", category.getName());
		assertEquals("Category{name=a name}", category.toString());
	}
}

package greensopinion.finance.services.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import greensopinion.finance.services.domain.Categories;
import greensopinion.finance.services.domain.CategoriesService;
import greensopinion.finance.services.domain.Category;
import greensopinion.finance.services.web.model.CategoryModel;

public class CategoryWebServiceTest {

	private final CategoriesService categoriesService = mock(CategoriesService.class);
	private final CategoryWebService service = new CategoryWebService(categoriesService);
	private final Categories categories = createCategories();

	@Before
	public void before() {
		doReturn(categories).when(categoriesService).retrieve();
	}

	@Test
	public void list() {
		List<CategoryModel> list = service.list();
		assertNotNull(list);
		assertEquals(3, list.size());
		assertEquals("a", list.get(0).getName());
		assertEquals("B", list.get(1).getName());
		assertEquals("c", list.get(2).getName());
	}

	private Categories createCategories() {
		List<Category> values = new ArrayList<>();
		values.add(new Category("a"));
		values.add(new Category("c"));
		values.add(new Category("B"));
		return new Categories(values);
	}
}

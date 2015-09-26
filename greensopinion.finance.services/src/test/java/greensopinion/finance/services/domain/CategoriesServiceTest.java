package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.ValidationException;
import greensopinion.finance.services.persistence.CategoriesPersistenceService;

public class CategoriesServiceTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	private final CategoriesPersistenceService persistenceService = mock(CategoriesPersistenceService.class);
	private final CategoriesService categoriesService = new CategoriesService(persistenceService);
	private final Categories categories = new Categories(ImmutableList.of(new Category("A")));

	private Categories savedCategories;

	@Before
	public void before() {
		doReturn(categories).when(persistenceService).load();
		doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				savedCategories = invocation.getArgumentAt(0, Categories.class);
				return null;
			}
		}).when(persistenceService).save(any(Categories.class));
	}

	@Test
	public void createNull() {
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Must provide a category.");
		categoriesService.create(null);
	}

	@Test
	public void createNoName() {
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Category name must be specified.");
		categoriesService.create(new Category(""));
	}

	@Test
	public void createDuplicateName() {
		thrown.expect(ValidationException.class);
		thrown.expectMessage(
				"Category with name \"a\" cannot be created since a category with the same name already exists.");
		categoriesService.create(new Category("a "));
	}

	@Test
	public void create() {
		categoriesService.create(new Category(" B"));

		assertNotNull(savedCategories);
		assertEquals(2, savedCategories.getCategories().size());
		assertNotNull(savedCategories.getCategoryByName("B"));
		assertNotNull(savedCategories.getCategoryByName("A"));
	}

	@Test
	public void deleteByNameNoName() {
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Category name must be specified.");
		categoriesService.deleteByName(" ");
	}

	@Test
	public void deleteByNameNotFound() {
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Category with name \"not-present\" not found.");
		categoriesService.deleteByName("not-present");
	}

	@Test
	public void deleteByName() {
		categoriesService.deleteByName("a");
		assertNotNull(savedCategories);
		assertEquals(ImmutableList.of(), savedCategories.getCategories());
	}
}

package greensopinion.finance.services.persistence;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;

import greensopinion.finance.services.TestResources;
import greensopinion.finance.services.domain.Categories;
import greensopinion.finance.services.domain.Category;

public class CategoriesPersistenceServiceTest {

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File dataFolder;
	private CategoriesPersistenceService service;

	private DataDirectoryLocator dataDirectory;

	@Before
	public void before() {
		dataFolder = new File(temporaryFolder.getRoot(), "data");
		dataDirectory = new DataDirectoryLocator() {
			@Override
			public File locate() {
				return dataFolder;
			}
		};
		service = createService();
	}

	@Test
	public void defaultInstance() {
		Categories defaultInstance = service.defaultInstance();
		checkNotNull(defaultInstance);
		assertFalse(defaultInstance.getCategories().isEmpty());
	}

	@Test
	public void settingsRoundTrip() throws IOException {
		assertFalse(dataFolder.exists());

		Category category = new Category("a");
		Categories data = new Categories(ImmutableList.of(category));

		service.save(data);
		assertTrue(dataFolder.exists());

		assertEquals(TestResources.load(SettingsPersistenceServiceTest.class, "expected-categories.json.txt"),
				Files.toString(new File(dataFolder, "categories.json"), StandardCharsets.UTF_8));

		Categories loaded = service.load();
		assertNotNull(loaded);
		assertEquals(1, loaded.getCategories().size());
		assertEquals("a", loaded.getCategories().get(0).getName());
	}

	private CategoriesPersistenceService createService() {
		return new CategoriesPersistenceService(MockPersistenceGsonProvider.create(), dataDirectory);
	}
}

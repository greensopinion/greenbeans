/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.persistence;

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
import greensopinion.finance.services.domain.MatchRule;

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
		Categories categories = service.defaultInstance();
		assertDefaultCategories(categories);
	}

	@Test
	public void loadDefaultInstance() {
		Categories categories = service.load();
		assertDefaultCategories(categories);
	}

	private void assertDefaultCategories(Categories categories) {
		assertNotNull(categories);
		assertFalse(categories.getCategories().isEmpty());
		assertNotNull(categories.getCategoryByName("Groceries"));
	}

	@Test
	public void settingsRoundTrip() throws IOException {
		assertFalse(dataFolder.exists());

		MatchRule rule = MatchRule.withPattern("abc");

		Category categoryA = new Category("a");
		Category categoryB = new Category("b").withMatchRule(rule);
		Categories data = new Categories(ImmutableList.of(categoryA, categoryB));

		service.save(data);
		assertTrue(dataFolder.exists());

		assertEquals(TestResources.load(SettingsPersistenceServiceTest.class, "expected-categories.json.txt"),
				Files.toString(new File(dataFolder, "categories.json"), StandardCharsets.UTF_8));

		Categories loaded = service.load();
		assertNotNull(loaded);
		assertEquals(2, loaded.getCategories().size());
		assertEquals("a", loaded.getCategories().get(0).getName());
		assertEquals(ImmutableList.of(), loaded.getCategories().get(0).getMatchRules());
		assertEquals("b", loaded.getCategories().get(1).getName());
		assertEquals(ImmutableList.of(rule), loaded.getCategories().get(1).getMatchRules());
	}

	private CategoriesPersistenceService createService() {
		return new CategoriesPersistenceService(MockPersistenceGsonProvider.create(), dataDirectory);
	}
}

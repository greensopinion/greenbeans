/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.greensopinion.finance.services.domain;

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
import com.greensopinion.finance.services.ValidationException;
import com.greensopinion.finance.services.domain.Categories;
import com.greensopinion.finance.services.domain.CategoriesService;
import com.greensopinion.finance.services.domain.Category;
import com.greensopinion.finance.services.domain.EntityEventSupport;
import com.greensopinion.finance.services.domain.MatchRule;
import com.greensopinion.finance.services.persistence.CategoriesPersistenceService;

public class CategoriesServiceTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	private final CategoriesPersistenceService persistenceService = mock(CategoriesPersistenceService.class);
	private final CategoriesService categoriesService = new CategoriesService(persistenceService,
			new EntityEventSupport());
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

	@Test
	public void addRuleByNameNotFound() {
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Category name must be specified.");
		categoriesService.addRuleByName(" ", MatchRule.withPattern("yo"));
	}

	@Test
	public void addRuleByName() {
		MatchRule matchRule = MatchRule.withPattern("yo");
		categoriesService.addRuleByName("a", matchRule);

		Category category = savedCategories.getCategoryByName("a");
		assertEquals(ImmutableList.of(matchRule), category.getMatchRules());
	}
}

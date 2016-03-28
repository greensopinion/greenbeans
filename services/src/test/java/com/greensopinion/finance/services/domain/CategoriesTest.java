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
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.greensopinion.finance.services.domain.Categories;
import com.greensopinion.finance.services.domain.Category;

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

	@Test
	public void foo() {
		Category category1 = new Category("a");
		Category category2 = new Category("b");
		Categories categories = new Categories(ImmutableList.of(category1, category2));

		assertNull(categories.getCategoryByName("nope"));
		assertEquals(category1, categories.getCategoryByName("a"));
		assertEquals(category1, categories.getCategoryByName("A"));
	}
}

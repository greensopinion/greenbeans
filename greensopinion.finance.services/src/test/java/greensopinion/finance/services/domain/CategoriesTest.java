/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

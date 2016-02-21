/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.domain;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class Categories {

	private final List<Category> categories;

	public Categories() {
		categories = ImmutableList.of();
	}

	public Categories(List<Category> categories) {
		this.categories = ImmutableList.copyOf(categories);
	}

	public List<Category> getCategories() {
		return categories;
	}

	public Category getCategoryByName(String name) {
		for (Category category : getCategories()) {
			if (category.getName().equalsIgnoreCase(name.trim())) {
				return category;
			}
		}
		return null;
	}
}

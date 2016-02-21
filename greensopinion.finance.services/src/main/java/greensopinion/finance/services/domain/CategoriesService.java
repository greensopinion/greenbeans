/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.domain;

import static greensopinion.finance.services.ValidationPreconditions.validate;
import static greensopinion.finance.services.ValidationPreconditions.validateNotNull;
import static greensopinion.finance.services.ValidationPreconditions.validateRequired;
import static java.text.MessageFormat.format;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.persistence.CategoriesPersistenceService;
import greensopinion.finance.services.persistence.ConfigurationService;

public class CategoriesService extends ConfigurationService<Categories> {

	@Inject
	CategoriesService(CategoriesPersistenceService persistenceService, EntityEventSupport eventSupport) {
		super(persistenceService, eventSupport);
	}

	public void create(Category category) {
		validateNotNull(category, "Must provide a category.");
		validateRequired(category.getName(), "Category name");

		String name = category.getName().trim();
		Categories categories = retrieve();
		validate(categories.getCategoryByName(name) == null,
				format("Category with name \"{0}\" cannot be created since a category with the same name already exists.",
						name));

		Categories newCategories = new Categories(
				ImmutableList.<Category> builder().addAll(categories.getCategories()).add(new Category(name)).build());
		update(newCategories);
	}

	public void deleteByName(String name) {
		Categories categories = retrieve();
		Category category = findCategoryByName(categories, name);

		List<Category> values = new ArrayList<>(categories.getCategories());
		values.remove(category);

		update(new Categories(values));
	}

	public void addRuleByName(String name, MatchRule matchRule) {
		Categories categories = retrieve();
		Category category = findCategoryByName(categories, name);

		List<Category> values = new ArrayList<>(categories.getCategories());
		int indexOf = values.indexOf(category);
		values.set(indexOf, category.withMatchRule(matchRule));

		update(new Categories(values));
	}

	private Category findCategoryByName(Categories categories, String name) {
		validateRequired(name, "Category name");

		Category category = categories.getCategoryByName(name.trim());
		validate(category != null, format("Category with name \"{0}\" not found.", name));
		return category;
	}
}

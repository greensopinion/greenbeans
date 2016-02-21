/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web;

import static com.google.common.base.Preconditions.checkNotNull;
import static greensopinion.finance.services.ValidationPreconditions.validateNotNull;
import static greensopinion.finance.services.ValidationPreconditions.validateNotNullOrEmpty;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Ordering;

import greensopinion.finance.services.domain.CategoriesService;
import greensopinion.finance.services.domain.Category;
import greensopinion.finance.services.domain.MatchRule;
import greensopinion.finance.services.web.model.CategoryModel;
import greensopinion.finance.services.web.model.CategoryRuleModel;

@Path(CategoryWebService.BASE_PATH)
public class CategoryWebService {
	static final String BASE_PATH = "/categories";

	private final CategoriesService categoriesService;

	@Inject
	CategoryWebService(CategoriesService categoriesService) {
		this.categoriesService = checkNotNull(categoriesService);
	}

	@GET
	public List<CategoryModel> list() {
		return FluentIterable.from(categoriesService.retrieve().getCategories())
				.transform(new Function<Category, CategoryModel>() {

					@Override
					public CategoryModel apply(Category category) {
						return new CategoryModel(category.getName());
					}
				}).toSortedList(Ordering.natural());
	}

	@POST
	public void create(CategoryModel model) {
		validateNotNull(model, "Must provide a category.");
		categoriesService.create(new Category(model.getName()));
	}

	@DELETE
	@Path("{name}")
	public void delete(@PathParam("name") String name) {
		categoriesService.deleteByName(name);
	}

	@POST
	@Path("{name}/rules")
	public void addRule(@PathParam("name") String name, CategoryRuleModel ruleModel) {
		validateNotNull(ruleModel, "Must provide a rule model.");

		categoriesService.addRuleByName(name, toMatchRule(ruleModel));
	}

	private MatchRule toMatchRule(CategoryRuleModel ruleModel) {
		String matchDescription = ruleModel.getMatchDescription();
		validateNotNullOrEmpty(matchDescription, "Description text must be provided.");
		return MatchRule.withPatternFromText(matchDescription);
	}
}

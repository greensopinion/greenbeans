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
package com.greensopinion.finance.services.web;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.greensopinion.finance.services.ValidationPreconditions.validateNotNull;
import static com.greensopinion.finance.services.ValidationPreconditions.validateNotNullOrEmpty;

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
import com.greensopinion.finance.services.domain.CategoriesService;
import com.greensopinion.finance.services.domain.Category;
import com.greensopinion.finance.services.domain.MatchRule;
import com.greensopinion.finance.services.web.model.CategoryModel;
import com.greensopinion.finance.services.web.model.CategoryRuleModel;

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

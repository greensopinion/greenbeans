package greensopinion.finance.services.domain;

import static greensopinion.finance.services.ValidationPreconditions.validate;
import static greensopinion.finance.services.ValidationPreconditions.validateNotNull;
import static greensopinion.finance.services.ValidationPreconditions.validateRequired;
import static java.text.MessageFormat.format;

import javax.inject.Inject;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.persistence.CategoriesPersistenceService;
import greensopinion.finance.services.persistence.ConfigurationService;

public class CategoriesService extends ConfigurationService<Categories> {

	@Inject
	CategoriesService(CategoriesPersistenceService persistenceService) {
		super(persistenceService);
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
		super.update(newCategories);
	}
}

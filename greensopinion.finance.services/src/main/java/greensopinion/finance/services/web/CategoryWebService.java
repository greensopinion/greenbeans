package greensopinion.finance.services.web;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

import greensopinion.finance.services.domain.CategoriesService;
import greensopinion.finance.services.domain.Category;
import greensopinion.finance.services.web.model.CategoryModel;

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
				}).toList();
	}
}

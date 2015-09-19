package greensopinion.finance.services.web;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import greensopinion.finance.services.web.model.CategoryModel;

@Path(CategoryWebService.BASE_PATH)
public class CategoryWebService {
	static final String BASE_PATH = "/categories";

	@GET
	public List<CategoryModel> list() {
		List<CategoryModel> categories = new ArrayList<>();

		categories.add(new CategoryModel("Bank Fees"));
		categories.add(new CategoryModel("Books"));
		categories.add(new CategoryModel("Business Expenses"));
		categories.add(new CategoryModel("Charity"));
		categories.add(new CategoryModel("Clothing"));
		categories.add(new CategoryModel("Dog"));
		categories.add(new CategoryModel("Dry Cleaning"));
		categories.add(new CategoryModel("Entertainment"));
		categories.add(new CategoryModel("Government (Returns)"));
		categories.add(new CategoryModel("Groceries"));
		categories.add(new CategoryModel("House"));
		categories.add(new CategoryModel("Insurance"));
		categories.add(new CategoryModel("Investments"));
		categories.add(new CategoryModel("Medical"));
		categories.add(new CategoryModel("Misc"));
		categories.add(new CategoryModel("Mortgage"));
		categories.add(new CategoryModel("Pay"));
		categories.add(new CategoryModel("Payment/Transfer"));
		categories.add(new CategoryModel("Personal"));
		categories.add(new CategoryModel("Transportation"));
		categories.add(new CategoryModel("Sports"));
		categories.add(new CategoryModel("Taxes"));
		categories.add(new CategoryModel("Utilities"));
		categories.add(new CategoryModel("Uncategorized"));

		return categories;
	}
}

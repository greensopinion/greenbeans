package greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class CategoryModel {
	private final String name;

	public CategoryModel(String name) {
		this.name = checkNotNull(name);
	}

	public String getName() {
		return name;
	}
}

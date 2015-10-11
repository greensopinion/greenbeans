package greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class CategoryModel implements Comparable<CategoryModel> {
	private final String name;

	public CategoryModel(String name) {
		this.name = checkNotNull(name);
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(CategoryModel o) {
		if (o == this) {
			return 0;
		}
		return name.compareToIgnoreCase(o.name);
	}
}

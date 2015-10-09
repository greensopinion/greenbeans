package greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;

public class Category {
	private final String name;

	public Category(String name) {
		this.name = checkNotNull(name);
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(Category.class).add("name", name).toString();
	}
}

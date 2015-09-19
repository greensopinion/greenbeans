package greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkNotNull;

public class Category {
	private final String name;

	public Category(String name) {
		this.name = checkNotNull(name);
	}

	public String getName() {
		return name;
	}
}

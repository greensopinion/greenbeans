/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
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

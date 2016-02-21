/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class PeriodDetails {
	private final String name;
	private final List<CategorySummary> categories;

	public PeriodDetails(String name, List<CategorySummary> categories) {
		this.name = checkNotNull(name);
		this.categories = ImmutableList.copyOf(categories);
	}

	public String getName() {
		return name;
	}

	public List<CategorySummary> getCategories() {
		return categories;
	}
}

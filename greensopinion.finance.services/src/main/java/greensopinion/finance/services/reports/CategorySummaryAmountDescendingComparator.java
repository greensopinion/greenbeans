/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.reports;

import java.util.Comparator;

import greensopinion.finance.services.web.model.CategorySummary;

class CategorySummaryAmountDescendingComparator implements Comparator<CategorySummary> {

	@Override
	public int compare(CategorySummary o1, CategorySummary o2) {
		int i = Long.valueOf(o1.getAmount()).compareTo(o2.getAmount());
		if (i == 0) {
			i = o1.getName().compareToIgnoreCase(o2.getName());
		}
		return i;
	}
}

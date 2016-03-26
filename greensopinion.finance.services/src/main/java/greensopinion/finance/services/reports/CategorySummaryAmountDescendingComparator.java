/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

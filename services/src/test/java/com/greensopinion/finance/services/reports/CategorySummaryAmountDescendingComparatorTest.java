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
package com.greensopinion.finance.services.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.greensopinion.finance.services.reports.CategorySummaryAmountDescendingComparator;
import com.greensopinion.finance.services.web.model.CategorySummary;

public class CategorySummaryAmountDescendingComparatorTest {
	private final CategorySummaryAmountDescendingComparator comparator = new CategorySummaryAmountDescendingComparator();

	@Test
	public void compareTo() {
		CategorySummary summary1 = new CategorySummary("one", -1);
		CategorySummary summary2 = new CategorySummary("two", -2);
		assertEquals(0, comparator.compare(summary1, summary1));
		assertEquals(0, comparator.compare(summary2, summary2));
		assertEquals(1, comparator.compare(summary1, summary2));
		assertEquals(-1, comparator.compare(summary2, summary1));
	}
}

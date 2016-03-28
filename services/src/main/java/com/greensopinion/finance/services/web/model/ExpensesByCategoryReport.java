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
package com.greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class ExpensesByCategoryReport extends Report {
	private final List<PeriodDetails> monthlyDetails = new ArrayList<>();

	public ExpensesByCategoryReport() {
		super("Monthly Expenses By Category");
	}

	public void add(PeriodDetails detailsForMonth) {
		monthlyDetails.add(checkNotNull(detailsForMonth));
	}

	public List<PeriodDetails> getMonthlyDetails() {
		return ImmutableList.copyOf(monthlyDetails);
	}
}

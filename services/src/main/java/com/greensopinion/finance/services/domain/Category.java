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
package com.greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

public class Category {
	private final String name;
	private final List<MatchRule> matchRules;

	public Category(String name) {
		this(name, ImmutableList.of());
	}

	public Category(String name, List<MatchRule> matchRules) {
		this.name = checkNotNull(name);
		this.matchRules = ImmutableList.copyOf(checkNotNull(matchRules));
	}

	public String getName() {
		return name;
	}

	public List<MatchRule> getMatchRules() {
		return MoreObjects.firstNonNull(matchRules, ImmutableList.<MatchRule> of());
	}

	public boolean matches(Transaction transaction) {
		checkNotNull(transaction);
		if (!Strings.isNullOrEmpty(transaction.getCategoryName())) {
			return getName().equals(transaction.getCategoryName());
		}
		for (MatchRule matchRule : getMatchRules()) {
			if (matchRule.matches(transaction)) {
				return true;
			}
		}
		return false;
	}

	public Category withMatchRule(MatchRule rule) {
		checkNotNull(rule);
		ImmutableList.Builder<MatchRule> newRules = ImmutableList.builder();
		newRules.addAll(getMatchRules());
		newRules.add(rule);
		return new Category(name, newRules.build());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(Category.class).add("name", name).toString();
	}
}

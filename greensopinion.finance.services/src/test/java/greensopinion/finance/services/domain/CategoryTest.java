/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.transaction.MockTransaction;

public class CategoryTest {

	@Test
	public void create() {
		Category category = new Category("a name");
		assertEquals("a name", category.getName());
		assertEquals("Category{name=a name}", category.toString());
		assertEquals(ImmutableList.of(), category.getMatchRules());
	}

	@Test
	public void createWithMatchRules() {
		List<MatchRule> rules = ImmutableList.of(MatchRule.withPattern("abc"), MatchRule.withPattern("def"));
		Category category = new Category("a name", rules);
		assertEquals(rules, category.getMatchRules());
	}

	@Test
	public void withMatchRule() {
		MatchRule rule = MatchRule.withPattern("abc");
		MatchRule rule2 = MatchRule.withPattern("abc2");
		Category category = new Category("a name");
		Category category2 = category.withMatchRule(rule);
		assertNotNull(category2);
		assertNotSame(category, category2);
		assertEquals(category.getName(), category2.getName());
		assertEquals(ImmutableList.of(), category.getMatchRules());
		assertEquals(ImmutableList.of(rule), category2.getMatchRules());
		assertEquals(ImmutableList.of(rule, rule2), category2.withMatchRule(rule2).getMatchRules());
	}

	@Test
	public void matches() {
		MatchRule rule = MatchRule.withPattern("abc");
		MatchRule rule2 = MatchRule.withPattern("def");
		Category category = new Category("a name", ImmutableList.of(rule, rule2));
		assertTrue(category.matches(MockTransaction.create("2015-01-01", "aaabc", 123)));
		assertTrue(category.matches(MockTransaction.create("2015-01-01", "def", 123)));
		assertFalse(category.matches(MockTransaction.create("2015-01-01", "ghi", 123)));
		assertFalse(new Category("nope").matches(MockTransaction.create("2015-01-01", "def", 123)));
	}

	@Test
	public void matchesWithCategoryName() {
		Transaction transactionWithoutCategoryName = MockTransaction.create("2015-01-01", "abc", 123);
		Transaction transactionWithCategoryName = transactionWithoutCategoryName.withCategoryName("a name");
		MatchRule rule = MatchRule.withPattern("abc");
		Category category = new Category("a name");
		Category categoryWithRule = new Category("second name").withMatchRule(rule);

		assertTrue(category.matches(transactionWithCategoryName));
		assertFalse(categoryWithRule.matches(transactionWithCategoryName));
		assertTrue(categoryWithRule.matches(transactionWithoutCategoryName));
	}
}

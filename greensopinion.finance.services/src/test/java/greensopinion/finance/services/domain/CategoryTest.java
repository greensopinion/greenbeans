package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

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
}

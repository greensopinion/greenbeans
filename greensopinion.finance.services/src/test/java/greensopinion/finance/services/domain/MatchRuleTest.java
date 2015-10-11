package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import greensopinion.finance.services.transaction.MockTransaction;

public class MatchRuleTest {

	@Test
	public void withPattern() {
		MatchRule matchRule = MatchRule.withPattern("abc");
		assertNotNull(matchRule);
		assertEquals("abc", matchRule.getPattern());
		assertEquals("MatchRule{pattern=abc}", matchRule.toString());
	}

	@Test
	public void foO() {
		MatchRule matchRule = MatchRule.withPatternFromText(" A  B \n\\  ");
		assertEquals("A\\s+B\\s+\\Q\\\\E", matchRule.getPattern());
	}

	@Test
	public void hashCodeEquals() {
		MatchRule matchRule = MatchRule.withPattern("abc");
		MatchRule matchRule2 = MatchRule.withPattern("def");

		assertFalse(matchRule.equals(null));
		assertFalse(matchRule.equals(""));
		assertFalse(matchRule.equals(matchRule2));

		assertEquality(matchRule, matchRule);
		assertEquality(matchRule, MatchRule.withPattern("abc"));

		assertTrue(matchRule.equals(MatchRule.withPattern("abc")));
	}

	@Test
	public void matches() {
		MatchRule matchRule = MatchRule.withPattern("abc");
		assertMatches(matchRule, "123 abc def");
		assertMatches(matchRule, "abc");
		assertMatches(matchRule, "ABC");
		assertMatches(matchRule, "ABc");
		assertMatches(matchRule, "1234abcfsdf");
		assertNoMatches(matchRule, "absdfc");
	}

	private void assertNoMatches(MatchRule matchRule, String description) {
		assertEquals(false, matchRule.matches(MockTransaction.create("2015-01-01", description, 1234)));
	}

	private void assertMatches(MatchRule matchRule, String description) {
		assertEquals(true, matchRule.matches(MockTransaction.create("2015-01-01", description, 1234)));
	}

	private void assertEquality(MatchRule matchRule, MatchRule matchRule2) {
		assertTrue(matchRule.equals(matchRule2));
		assertTrue(matchRule2.equals(matchRule));
		assertEquals(matchRule.hashCode(), matchRule2.hashCode());
	}
}

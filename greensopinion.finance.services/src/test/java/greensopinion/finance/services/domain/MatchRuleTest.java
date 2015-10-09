package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MatchRuleTest {

	@Test
	public void withPattern() {
		MatchRule matchRule = MatchRule.withPattern("abc");
		assertNotNull(matchRule);
		assertEquals("abc", matchRule.getPattern());
		assertEquals("MatchRule{pattern=abc}", matchRule.toString());
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

	private void assertEquality(MatchRule matchRule, MatchRule matchRule2) {
		assertTrue(matchRule.equals(matchRule2));
		assertTrue(matchRule2.equals(matchRule));
		assertEquals(matchRule.hashCode(), matchRule2.hashCode());
	}
}

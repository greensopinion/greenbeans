package greensopinion.finance.services.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class WebPathTest {

	@Test
	public void noVariables() {
		WebPath webPath = new WebPath("GET", "/one");
		assertEquals("\\Q/one\\E", webPath.getPattern().toString());
		assertEquals(ImmutableList.of(), webPath.getVariableNames());
	}

	@Test
	public void oneVariable() {
		WebPath webPath = new WebPath("GET", "/one/{two}");
		assertEquals("\\Q/one/\\E([^/]*)", webPath.getPattern().toString());
		assertEquals(ImmutableList.of("two"), webPath.getVariableNames());

		assertMatch(ImmutableMap.of("two", "abc"), "/one/abc", webPath);
		assertMatch(ImmutableMap.of("two", ""), "/one/", webPath);
		assertNoMatch("/one", webPath);
		assertNoMatch("/one/two/three", webPath);
	}

	@Test
	public void oneVariableWithTrailing() {
		WebPath webPath = new WebPath("GET", "/one/{two}/three");
		assertEquals("\\Q/one/\\E([^/]*)\\Q/three\\E", webPath.getPattern().toString());
		assertEquals(ImmutableList.of("two"), webPath.getVariableNames());

		assertMatch(ImmutableMap.of("two", "abc"), "/one/abc/three", webPath);
		assertNoMatch("/one/a/bc/three", webPath);
		assertNoMatch("/one/a/bc", webPath);
		assertNoMatch("/one/abc/", webPath);
	}

	@Test
	public void twoVariables() {
		WebPath webPath = new WebPath("GET", "/one/{two}/something/{three}");
		assertEquals("\\Q/one/\\E([^/]*)\\Q/something/\\E([^/]*)", webPath.getPattern().toString());
		assertEquals(ImmutableList.of("two", "three"), webPath.getVariableNames());

		assertMatch(ImmutableMap.of("two", "abc", "three", "def"), "/one/abc/something/def", webPath);
		assertMatch(ImmutableMap.of("two", "abc", "three", ""), "/one/abc/something/", webPath);
		assertNoMatch("/one/abc/something", webPath);
		assertNoMatch("/one/abc/something/def/", webPath);
	}

	private void assertNoMatch(String path, WebPath webPath) {
		MatchResult match = webPath.match(webPath.getHttpMethod(), path);
		assertNoMatch(match);
	}

	private void assertNoMatch(MatchResult match) {
		assertNotNull(match);
		assertFalse(match.matches());
		assertEquals(ImmutableMap.of(), match.getVariables());
		assertSame(MatchResult.NO_MATCH, match);
	}

	private void assertMatch(Map<String, String> variables, String path, WebPath webPath) {
		MatchResult match = webPath.match(webPath.getHttpMethod(), path);
		assertNotNull(match);
		assertTrue(match.matches());
		assertEquals(variables, match.getVariables());

		assertNoMatch(webPath.match("NOT" + webPath.getHttpMethod(), path));
	}
}

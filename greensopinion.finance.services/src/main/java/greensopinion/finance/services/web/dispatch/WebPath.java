package greensopinion.finance.services.web.dispatch;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.MoreObjects;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class WebPath {
	private final String httpMethod;
	private Pattern pattern;
	private List<String> variableNames;

	public WebPath(String httpMethod, String path) {
		this.httpMethod = checkNotNull(httpMethod);
		checkArgument(!httpMethod.isEmpty());
		compile(checkNotNull(path));
	}

	public MatchResult match(String method, String path) {
		if (!httpMethod.equals(method)) {
			return MatchResult.NO_MATCH;
		}
		Matcher matcher = pattern.matcher(path);
		if (matcher.matches()) {
			ImmutableMap.Builder<String, String> variablesBuilder = ImmutableMap.builder();
			for (int x = 0; x < variableNames.size(); ++x) {
				String variable = matcher.group(x + 1);
				variablesBuilder.put(variableNames.get(x), decode(variable));
			}
			return new MatchResult(true, variablesBuilder.build());
		}
		return MatchResult.NO_MATCH;
	}

	private String decode(String variable) {
		try {
			return URLDecoder.decode(variable, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw Throwables.propagate(e);
		}
	}

	private void compile(String path) {
		Pattern variableMatcher = Pattern.compile("\\{([^{}]+)\\}");
		String regex = "";
		int offset = 0;
		ImmutableList.Builder<String> variableNameBuilder = ImmutableList.builder();
		Matcher matcher = variableMatcher.matcher(path);
		while (matcher.find()) {
			int start = matcher.start();
			if (start > offset) {
				regex += Pattern.quote(path.substring(offset, start));
			}
			variableNameBuilder.add(matcher.group(1));
			regex += "([^/]*)";
			offset = matcher.end();
		}
		if (offset < path.length()) {
			regex += Pattern.quote(path.substring(offset));
		}
		this.variableNames = variableNameBuilder.build();
		this.pattern = Pattern.compile(regex);
	}

	List<String> getVariableNames() {
		return variableNames;
	}

	Pattern getPattern() {
		return pattern;
	}

	String getHttpMethod() {
		return httpMethod;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(WebPath.class).add("httpMethod", httpMethod).add("pattern", pattern)
				.toString();
	}
}

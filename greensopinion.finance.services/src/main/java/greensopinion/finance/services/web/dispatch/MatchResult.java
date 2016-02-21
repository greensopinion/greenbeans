/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.dispatch;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class MatchResult {

	static final MatchResult NO_MATCH = new MatchResult(false, ImmutableMap.of());

	private final boolean matches;
	private final Map<String, String> variables;

	MatchResult(boolean matches, Map<String, String> variables) {
		this.matches = matches;
		this.variables = ImmutableMap.copyOf(variables);
	}

	public boolean matches() {
		return matches;
	}

	public Map<String, String> getVariables() {
		return variables;
	}
}

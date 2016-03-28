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

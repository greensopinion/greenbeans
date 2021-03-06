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
package com.greensopinion.finance.services.transaction;

import static com.google.common.base.Preconditions.checkState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

public class EntityReferences {

	private static EntityReferences instance = new EntityReferences();

	private static ListMultimap<String, String> readEntityReferences() {
		ImmutableListMultimap.Builder<String, String> builder = ImmutableListMultimap.builder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					EntityReferences.class.getResourceAsStream("entity-references.txt"), Charsets.UTF_8)); //$NON-NLS-1$
			try {
				Splitter splitter = Splitter.on(CharMatcher.WHITESPACE).trimResults().omitEmptyStrings();

				String line;
				while ((line = reader.readLine()) != null) {
					List<String> lineItems = splitter.splitToList(line);
					checkState(lineItems.size() > 1);
					for (int x = 1; x < lineItems.size(); ++x) {
						builder.put(lineItems.get(0), lineItems.get(x));
					}
				}
			} finally {
				reader.close();
			}
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
		return builder.build();
	}

	public static EntityReferences instance() {
		return instance;
	}

	private final ListMultimap<String, String> nameToNumericEntityReferences;

	private EntityReferences() {
		nameToNumericEntityReferences = readEntityReferences();
	}

	public List<String> nameToEntityReferences(String name) {
		return nameToNumericEntityReferences.get(name);
	}

	public String namedEntityToString(String name) {
		if (Strings.isNullOrEmpty(name)) {
			return "";
		}
		String s = "";
		List<String> entityReferences = nameToEntityReferences(name);
		if (entityReferences.isEmpty() && name.startsWith("#")) {
			entityReferences = ImmutableList.of(name);
		}
		for (String reference : entityReferences) {
			try {
				char c = (char) Integer.parseInt(reference.substring(1));
				s += c;
			} catch (NumberFormatException e) {
				// ignore
			}
		}
		return s;
	}
}

/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.common.io.Resources;

public class TestResources {
	public static String load(Class<?> relativeTo, String resourceName) {
		try {
			URL resource = relativeTo.getResource(resourceName);
			checkNotNull(resource, "Resource %s does not exist relative to %s", resourceName, relativeTo.getName());
			return Resources.toString(resource, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new IllegalStateException(
					format("Cannot load {0} relative to {1}", resourceName, relativeTo.getName()), e);
		}
	}

	private TestResources() {
		// prevent instantiation
	}
}

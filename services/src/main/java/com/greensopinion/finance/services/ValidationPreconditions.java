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
package com.greensopinion.finance.services;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

public class ValidationPreconditions {

	public static void validateNotNull(Object value, String message) {
		validate(value != null, message);
	}

	public static void validateNotNullOrEmpty(String value, String message) {
		validate(value != null && !value.trim().isEmpty(), message);
	}

	public static void validateRequired(String value, String propertyName) {
		validateNotNullOrEmpty(value, format("{0} must be specified.", propertyName));
	}

	public static void validate(boolean condition, String message) {
		checkMessage(message);
		if (!condition) {
			throw new ValidationException(message);
		}
	}

	private static void checkMessage(String message) {
		checkNotNull(message);
		checkArgument(!message.isEmpty());
		checkArgument(message.charAt(0) == Character.toUpperCase(message.charAt(0)),
				"Message must start with an upper-case letter");
		checkArgument(message.charAt(message.length() - 1) == '.', "Message must end with a period");
	}

	private ValidationPreconditions() {
		// prevent instantiation
	}

}

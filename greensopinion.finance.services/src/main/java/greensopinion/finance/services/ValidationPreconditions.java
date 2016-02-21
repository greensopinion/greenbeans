/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services;

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

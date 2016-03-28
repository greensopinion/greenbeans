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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.greensopinion.finance.services.ValidationException;
import com.greensopinion.finance.services.ValidationPreconditions;

public class ValidationPreconditionsTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void validateNotNullRequiresMessage() {
		thrown.expect(NullPointerException.class);
		ValidationPreconditions.validateNotNull(new Object(), null);
	}

	@Test
	public void validateNotNullRequiresMessageSentenceUpperCaseFirstLetter() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Message must start with an upper-case letter");
		ValidationPreconditions.validateNotNull(new Object(), "lower case first letter.");
	}

	@Test
	public void validateNotNullRequiresMessageSentenceEndsWithPeriod() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Message must end with a period");
		ValidationPreconditions.validateNotNull(new Object(), "Must have period");
	}

	@Test
	public void validateNotNull() {
		ValidationPreconditions.validateNotNull(new Object(), "Must provide a value.");
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Must provide a value.");
		ValidationPreconditions.validateNotNull(null, "Must provide a value.");
	}

	@Test
	public void validateNotNullOrEmpty() {
		ValidationPreconditions.validateNotNullOrEmpty("Not empty", "Must provide a value.");
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Must provide a value.");
		ValidationPreconditions.validateNotNullOrEmpty("   ", "Must provide a value.");
	}

	@Test
	public void validateNotNullOrEmptyNull() {
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Must provide a value.");
		ValidationPreconditions.validateNotNullOrEmpty(null, "Must provide a value.");
	}

	@Test
	public void validate() {
		ValidationPreconditions.validate(true, "No harm here.");
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Ouch.");
		ValidationPreconditions.validate(false, "Ouch.");

	}
}

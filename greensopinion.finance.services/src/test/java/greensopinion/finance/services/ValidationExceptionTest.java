/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidationExceptionTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void requiresMessage() {
		thrown.expect(NullPointerException.class);
		new ValidationException(null);
	}

	@Test
	public void getMessage() {
		assertEquals("a message", new ValidationException("a message").getMessage());
	}
}

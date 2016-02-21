/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import greensopinion.finance.services.web.model.ExceptionContent;

public class ExceptionContentTest {

	@Test
	public void simple() {
		ExceptionContent exceptionContent = new ExceptionContent(new Exception("arg"));
		assertEquals("arg", exceptionContent.getMessage());
		assertEquals("Exception", exceptionContent.getErrorCode());
	}

	@Test
	public void nested() {
		ExceptionContent exceptionContent = new ExceptionContent(new RuntimeException(new IOException("nope")));
		assertEquals("nope", exceptionContent.getMessage());
		assertEquals("RuntimeException", exceptionContent.getErrorCode());
	}

	@Test
	public void nested2() {
		ExceptionContent exceptionContent = new ExceptionContent(new RuntimeException(new IOException()));
		assertEquals("Unexpected exception: RuntimeException: IOException", exceptionContent.getMessage());
		assertEquals("RuntimeException", exceptionContent.getErrorCode());
	}
}

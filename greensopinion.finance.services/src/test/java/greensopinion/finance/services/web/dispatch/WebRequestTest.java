/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.dispatch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WebRequestTest {

	@Test
	public void create() {
		WebRequest webRequest = new WebRequest("GET", "/yo", "123");
		assertEquals("GET", webRequest.getHttpMethod());
		assertEquals("/yo", webRequest.getPath());
		assertEquals("123", webRequest.getEntity());
		assertEquals("WebRequest{httpMethod=GET, path=/yo}", webRequest.toString());
	}
}

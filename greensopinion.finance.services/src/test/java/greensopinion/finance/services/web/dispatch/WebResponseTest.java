/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.dispatch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import greensopinion.finance.services.web.dispatch.WebResponse;

public class WebResponseTest {
	@Test
	public void create() {
		WebResponse webResponse = new WebResponse(3, "123");
		assertEquals(3, webResponse.getResponseCode());
		assertEquals("123", webResponse.getEntity());
	}
}

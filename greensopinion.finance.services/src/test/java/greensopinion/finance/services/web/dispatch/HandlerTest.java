/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.dispatch;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.PathParam;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableMap;

public class HandlerTest {
	public static class TestWebService {
		public Object thrower() {
			throw new RuntimeException("ouch");
		}

		public String echo(@PathParam("message") String s) {
			return s;
		}
	}

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void invokeWithException() throws Exception {
		Handler handler = new Handler(new TestWebService(), TestWebService.class.getDeclaredMethod("thrower"));

		thrown.expect(RuntimeException.class);
		thrown.expectMessage("ouch");
		handler.invoke(ImmutableMap.of());
	}

	@Test
	public void invokeWithParameter() throws Exception {
		Handler handler = new Handler(new TestWebService(),
				TestWebService.class.getDeclaredMethod("echo", String.class));
		assertEquals("test it yeah!", handler.invoke(ImmutableMap.of("message", "test it yeah!")));
	}
}

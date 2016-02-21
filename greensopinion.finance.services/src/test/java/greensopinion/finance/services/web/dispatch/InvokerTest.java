/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.dispatch;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.PathParam;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import greensopinion.finance.services.web.GsonWebRenderer;

public class InvokerTest {

	public static class MockWebService {
		public void get(@PathParam("a") String a) {
		}

		public void getWithThrow() {
			throw new NullPointerException("test");
		}
	}

	private final Logger logger = mock(Logger.class);

	private final Invoker invoker = new Invoker(new GsonWebRenderer(new Gson()), logger);

	@Test
	public void convertEmptyString() throws Exception {
		assertEquals(null, invoker.convert(String.class, ""));
	}

	@Test
	public void createParametersWithEmptyString() throws Exception {
		Handler handler = createHandler("get");
		Map<String, Object> parameters = invoker.createParameters(new WebRequest("GET", "/path", null),
				new MatchResult(true, ImmutableMap.of("a", "")), handler);
		assertEquals(null, parameters.get("a"));
	}

	@Test
	public void invokeWithException() throws Exception {
		Handler handler = createHandler("getWithThrow");
		WebResponse response = invoker.invoke(mock(WebRequest.class), new MatchResult(true, ImmutableMap.of()),
				handler);
		assertEquals(500, response.getResponseCode());
		assertEquals("{\"errorCode\":\"NullPointerException\",\"message\":\"test\"}", response.getEntity());

		verify(logger).log(eq(Level.SEVERE), eq("test"), any(NullPointerException.class));
		verifyNoMoreInteractions(logger);
	}

	private Handler createHandler(String methodName) {
		MockWebService webService = new MockWebService();
		for (Method method : MockWebService.class.getDeclaredMethods()) {
			if (method.getName().equals(methodName)) {
				return new Handler(webService, method);
			}
		}
		throw new IllegalArgumentException(methodName);
	}
}

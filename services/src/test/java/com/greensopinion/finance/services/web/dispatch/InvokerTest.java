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
package com.greensopinion.finance.services.web.dispatch;

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
import com.greensopinion.finance.services.web.GsonWebRenderer;
import com.greensopinion.finance.services.web.dispatch.Handler;
import com.greensopinion.finance.services.web.dispatch.Invoker;
import com.greensopinion.finance.services.web.dispatch.MatchResult;
import com.greensopinion.finance.services.web.dispatch.WebRequest;
import com.greensopinion.finance.services.web.dispatch.WebResponse;

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

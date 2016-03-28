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

import javax.ws.rs.PathParam;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableMap;
import com.greensopinion.finance.services.web.dispatch.Handler;

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

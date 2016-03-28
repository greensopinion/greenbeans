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
package com.greensopinion.finance.services.bridge;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.greensopinion.finance.services.bridge.WebInvoker;
import com.greensopinion.finance.services.web.dispatch.WebDispatch;
import com.greensopinion.finance.services.web.dispatch.WebRequest;

public class WebInvokerTest {

	private final WebDispatch dispatch = mock(WebDispatch.class);
	private final WebInvoker invoker = new WebInvoker(dispatch);

	@Test
	public void invoke() {
		String requestPath = "/one/two%20three?four=five+six&seven=";
		invoker.invoke("GET", requestPath, null);
		WebRequest request = verifyDispatcherRequest();
		assertEquals("GET", request.getHttpMethod());
		assertEquals(null, request.getEntity());
		assertEquals(requestPath, request.getPath());
	}

	private WebRequest verifyDispatcherRequest() {
		ArgumentCaptor<WebRequest> requestCaptor = ArgumentCaptor.forClass(WebRequest.class);
		verify(dispatch).dispatch(requestCaptor.capture());
		return requestCaptor.getValue();
	}
}

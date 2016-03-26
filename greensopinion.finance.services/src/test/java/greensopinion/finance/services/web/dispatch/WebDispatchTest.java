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
package greensopinion.finance.services.web.dispatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import greensopinion.finance.services.GreenBeans;
import greensopinion.finance.services.ImportFilesService;
import greensopinion.finance.services.encryption.EncryptorService;
import greensopinion.finance.services.web.GsonWebRenderer;
import greensopinion.finance.services.web.WebServiceModule;

public class WebDispatchTest {

	static final class ThrowingInvoker extends Invoker {
		@Inject
		public ThrowingInvoker(GsonWebRenderer webRenderer) {
			super(webRenderer, mock(Logger.class));
		}

		@Override
		public WebResponse invoke(WebRequest request, MatchResult match, Handler handler) {
			throw new NullPointerException("this is a test");
		}
	}

	private final WebDispatch dispatch = createWebDispatch();

	@Test
	public void dispatch() {
		WebResponse response = dispatch.dispatch(new WebRequest("GET", "/abouts/current", null));
		assertNotNull(response);
		assertEquals(200, response.getResponseCode());
		int currentYear = LocalDate.now().get(ChronoField.YEAR);
		assertEquals("{\"applicationName\":\"" + GreenBeans.APP_NAME + "\",\"copyrightNotice\":\"Copyright (c) 2015, "
				+ currentYear + " David Green. All rights reserved.\"}", response.getEntity());
	}

	@Test
	public void dispatchNotFound() {
		WebResponse response = dispatch.dispatch(new WebRequest("GET", "/not-present", null));
		assertNotNull(response);
		assertEquals(404, response.getResponseCode());
		assertEquals("{\"errorCode\":\"NotFoundException\",\"message\":\"Not found: GET /not-present\"}",
				response.getEntity());
	}

	@Test
	public void dispatchRootPath() {
		WebResponse response = dispatch
				.dispatch(new WebRequest("POST", "/imports", "{files:['one','two'], deleteAfterImport:true}"));
		assertNotNull(response);
		assertEquals(204, response.getResponseCode());
	}

	@Test
	public void dispatchHasFaultBarrier() {
		WebDispatch dispatch = createWebDispatchWithThrowingInvoker();
		WebResponse response = dispatch.dispatch(new WebRequest("GET", "/abouts/current", null));
		assertNotNull(response);
		assertEquals(500, response.getResponseCode());
		assertEquals("{\"errorCode\":\"NullPointerException\",\"message\":\"this is a test\"}", response.getEntity());

		verify(dispatch.getLogger()).log(eq(Level.SEVERE), eq("Unexpected exception: this is a test"),
				any(NullPointerException.class));
	}

	@Test
	public void dispatchToEncryptionSettingsWebService() {
		WebResponse response = dispatch.dispatch(new WebRequest("PUT", "/encryption-settings", "{}"));
		assertNotNull(response);
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getResponseCode());
	}

	private WebDispatch createWebDispatchWithThrowingInvoker() {
		Module module = Modules.override(new WebServiceModule()).with(new AbstractModule() {

			@Override
			protected void configure() {
				bind(Invoker.class).to(ThrowingInvoker.class);
			}
		}, overrideWebServiceModule());
		Injector injector = Guice.createInjector(module);
		return new WebDispatch(injector, injector.getInstance(Invoker.class), mock(Logger.class));
	}

	private WebDispatch createWebDispatch() {
		return Guice.createInjector(new WebServiceModule(), overrideWebServiceModule()).getInstance(WebDispatch.class);
	}

	protected AbstractModule overrideWebServiceModule() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				bind(ImportFilesService.class).toInstance(mock(ImportFilesService.class));
				bind(EncryptorService.class).toInstance(mock(EncryptorService.class));
			}
		};
	}
}

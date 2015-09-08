package greensopinion.finance.services.web.dispatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.inject.Guice;

import greensopinion.finance.services.web.WebServiceModule;

public class WebDispatchTest {

	private final WebDispatch dispatch = createWebDispatch();

	@Test
	public void dispatch() {
		WebResponse response = dispatch.dispatch(new WebRequest("GET", "/abouts/current", null));
		assertNotNull(response);
		assertEquals(200, response.getResponseCode());
		assertEquals(
				"{\"applicationName\":\"Green's Opinion - Finances\",\"copyrightNotice\":\"Copyright (c) 2015 David Green.  All rights reserved.\"}",
				response.getEntity());
	}

	@Test
	public void dispatchNotFound() {
		WebResponse response = dispatch.dispatch(new WebRequest("GET", "/not-present", null));
		assertNotNull(response);
		assertEquals(404, response.getResponseCode());
		assertEquals("{\"errorCode\":\"NotFoundException\",\"message\":\"Not found: GET /not-present\"}",
				response.getEntity());
	}

	private WebDispatch createWebDispatch() {
		return Guice.createInjector(new WebServiceModule()).getInstance(WebDispatch.class);
	}
}

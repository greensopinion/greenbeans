package greensopinion.finance.services.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.inject.Guice;

public class WebDispatchTest {

	private WebDispatch dispatch = createWebDispatch();

	@Test
	public void dispatch() {
		WebResponse response = dispatch.dispatch(new WebRequest("GET", "/abouts/current", null));
		assertNotNull(response);
		assertEquals(200, response.getResponseCode());
		assertEquals(
				"{\"applicationName\":\"Green's Opinion - Finances\",\"copyrightNotice\":\"Copyright (c) 2015 David Green.  All rights reserved.\"}",
				response.getEntity());
	}

	private WebDispatch createWebDispatch() {
		return Guice.createInjector(new WebServiceModule()).getInstance(WebDispatch.class);
	}
}

package greensopinion.finance.services.web.dispatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

import greensopinion.finance.services.ImportFilesService;
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

	@Test
	public void dispatchRootPath() {
		WebResponse response = dispatch
				.dispatch(new WebRequest("POST", "/imports", "{files:['one','two'], deleteAfterImport:true}"));
		assertNotNull(response);
		assertEquals(204, response.getResponseCode());
	}

	private WebDispatch createWebDispatch() {
		return Guice.createInjector(new WebServiceModule(), new AbstractModule() {
			@Override
			protected void configure() {
				bind(ImportFilesService.class).toInstance(mock(ImportFilesService.class));
			}
		}).getInstance(WebDispatch.class);
	}
}

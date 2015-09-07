package greensopinion.finance.services.web;

import static greensopinion.finance.services.InjectorAsserts.assertSingletonBinding;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;

import greensopinion.finance.services.web.dispatch.Invoker;
import greensopinion.finance.services.web.dispatch.WebDispatch;

public class WebServiceModuleTest {
	@Test
	public void providesWebDispatch() {
		assertSingletonBinding(createInjector(), WebDispatch.class);
	}

	@Test
	public void providesGsonWebRenderer() {
		assertSingletonBinding(createInjector(), GsonWebRenderer.class);
	}

	@Test
	public void providesInvoker() {
		assertSingletonBinding(createInjector(), Invoker.class);
	}

	@Test
	public void providesAboutWebService() {
		assertSingletonBinding(createInjector(), AboutWebService.class);
	}

	@Test
	public void providesEncryptionSettingsWebService() {
		assertSingletonBinding(createInjector(), EncryptionSettingsWebService.class);
	}

	@Test
	public void providesGson() {
		Gson gson = assertSingletonBinding(createInjector(), Gson.class);
		assertHtmlEscapingDisabled(gson);
	}

	private void assertHtmlEscapingDisabled(Gson gson) {
		assertEquals("\"'\"", gson.toJson("'"));
	}

	private Injector createInjector() {
		return Guice.createInjector(new WebServiceModule());
	}
}

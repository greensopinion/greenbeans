package greensopinion.finance.services.application;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import greensopinion.finance.services.bridge.WebInvoker;

public class ServiceLocatorTest {

	@Test
	public void create() {
		WebInvoker invoker = mock(WebInvoker.class);
		ServiceLocator serviceLocator = new ServiceLocator(invoker);
		assertSame(invoker, serviceLocator.getWebInvoker());
	}
}

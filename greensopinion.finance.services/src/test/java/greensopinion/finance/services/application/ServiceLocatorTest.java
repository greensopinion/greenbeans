package greensopinion.finance.services.application;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ServiceLocatorTest {

	@Test
	public void create() {
		ServiceLocator serviceLocator = new ServiceLocator();
		assertNotNull(serviceLocator.getWebInvoker());
	}
}

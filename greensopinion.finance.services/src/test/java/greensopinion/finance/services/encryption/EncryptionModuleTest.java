package greensopinion.finance.services.encryption;

import static greensopinion.finance.services.InjectorAsserts.assertSingletonBinding;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class EncryptionModuleTest {
	@Test
	public void providesEncryptorProviderService() {
		assertSingletonBinding(createInjector(), EncryptorProviderService.class);
	}

	@Test
	public void providesEncryptorService() {
		assertSingletonBinding(createInjector(), EncryptorService.class);
	}

	@Test
	public void providesMasterPasswordChangeSupport() {
		assertSingletonBinding(createInjector(), EncryptorListener.class, MasterPasswordChangeSupport.class);
	}

	private Injector createInjector() {
		return Guice.createInjector(new EncryptionModule());
	}
}

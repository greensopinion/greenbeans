package greensopinion.finance.services;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import greensopinion.finance.services.application.Main;
import greensopinion.finance.services.encryption.EncryptorService;
import greensopinion.finance.services.persistence.DataDirectoryLocator;

public class AbstractIntegrationTest {
	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();
	protected Injector injector;

	@Before
	public void before() {
		injector = createInjector();
		initializeEncryptor();
	}

	protected Injector createInjector() {
		return Guice.createInjector(Modules.override(Main.applicationModules()).with(dataDirectoryLocatorModule()));
	}

	private Module dataDirectoryLocatorModule() {
		return new AbstractModule() {
			@Override
			protected void configure() {
				bind(DataDirectoryLocator.class).toInstance(new DataDirectoryLocator() {
					@Override
					public File locate() {
						return temporaryFolder.getRoot();
					}
				});
			}
		};
	}

	private void initializeEncryptor() {
		EncryptorService encryptor = injector.getInstance(EncryptorService.class);
		encryptor.configure("test");
	}
}

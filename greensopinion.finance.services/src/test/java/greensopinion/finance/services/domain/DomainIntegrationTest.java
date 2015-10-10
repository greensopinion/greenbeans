package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.collect.ImmutableList;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import greensopinion.finance.services.application.Main;
import greensopinion.finance.services.encryption.EncryptorService;
import greensopinion.finance.services.persistence.DataDirectoryLocator;

public class DomainIntegrationTest {
	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();
	private Injector injector;

	@Before
	public void before() {
		injector = Guice.createInjector(Modules.override(Main.applicationModules()).with(dataDirectoryLocatorModule()));
		initializeEncryptor();
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

	@Test
	public void transactionServiceIsEmpty() {
		TransactionsService transactionsService = injector.getInstance(TransactionsService.class);
		Transactions transactions = transactionsService.retrieve();
		assertNotNull(transactions);
		assertEquals(ImmutableList.of(), transactions.getTransactions());
	}
}
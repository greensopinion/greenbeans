package greensopinion.finance.services.data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;
import com.google.gson.GsonBuilder;

import greensopinion.finance.services.TestResources;
import greensopinion.finance.services.encryption.Encryptor;
import greensopinion.finance.services.encryption.EncryptorProviderService;
import greensopinion.finance.services.encryption.EncryptorSettings;
import greensopinion.finance.services.transaction.MockTransaction;
import greensopinion.finance.services.transaction.Transaction;

public class PersistenceServiceTest {
	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File dataFolder;
	private PersistenceService service;

	private DataDirectoryLocator dataDirectory;

	@Before
	public void before() {
		dataFolder = new File(temporaryFolder.getRoot(), "data");
		dataDirectory = new DataDirectoryLocator() {
			@Override
			public File locate() {
				return dataFolder;
			}
		};
		service = createService();
	}

	@Test
	public void readSettingsNoFile() {
		assertNotNull(service.loadSettings());
	}

	@Test
	public void readTransactionsNoFile() {
		assertNotNull(service.loadTransactions());
	}

	@Test
	public void settingsRoundTrip() throws IOException {
		assertFalse(dataFolder.exists());

		Settings data = new Settings();
		data.setEncryptorSettings(new EncryptorSettings("1234", new byte[] { 35, 36 }));

		service.saveSettings(data);
		assertTrue(dataFolder.exists());

		assertEquals(TestResources.load(PersistenceServiceTest.class, "expected-settings.json.txt"),
				Files.toString(new File(dataFolder, "settings.json"), StandardCharsets.UTF_8));

		Settings loaded = service.loadSettings();
		assertNotNull(loaded);
		assertEquals(data.getEncryptorSettings().getMasterPasswordVerificationState(),
				loaded.getEncryptorSettings().getMasterPasswordVerificationState());
		assertArrayEquals(data.getEncryptorSettings().getSalt(), loaded.getEncryptorSettings().getSalt());
	}

	@Test
	public void transactionsRoundTrip() throws IOException {
		File dataFile = new File(dataFolder, "transactions.json");
		assertFalse(dataFolder.exists());
		assertFalse(dataFile.exists());

		Transactions transactions = new Transactions(
				ImmutableList.of(MockTransaction.create("1995-12-15", "chistmas tree", -10100),
						MockTransaction.create("1995-12-14", "coffee", -500)));

		service.saveTransactions(transactions);
		assertTrue(dataFolder.exists());
		assertTrue(dataFile.exists());

		String fileContents = Files.toString(dataFile, StandardCharsets.UTF_8);
		for (Transaction txn : transactions.getTransactions()) {
			assertFalse(fileContents, fileContents.contains(txn.getDescription()));
			assertFalse(fileContents, fileContents.contains(Long.toString(txn.getAmount())));
		}

		Transactions loaded = service.loadTransactions();
		assertNotNull(loaded);
		assertEquals(transactions.getTransactions(), loaded.getTransactions());
	}

	private PersistenceService createService() {
		EncryptorProviderService encryptorProviderService = new EncryptorProviderService();

		String masterPassword = "test 123";
		Encryptor encryptor = new Encryptor(EncryptorSettings.newSettings(masterPassword), masterPassword);
		encryptorProviderService.setEncryptor(encryptor);

		return new PersistenceService(new PersistenceGsonProvider(encryptorProviderService) {
			@Override
			GsonBuilder builder() {
				return super.builder().setPrettyPrinting();
			}
		}, dataDirectory);
	}
}

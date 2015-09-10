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

import com.google.common.io.Files;
import com.google.gson.GsonBuilder;

import greensopinion.finance.services.TestResources;
import greensopinion.finance.services.encryption.EncryptorSettings;

public class DataServiceTest {
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
	public void readNoFile() {
		assertNotNull(service.loadSettings());
	}

	@Test
	public void roundTrip() throws IOException {
		assertFalse(dataFolder.exists());

		Settings data = new Settings();
		data.setEncryptorSettings(new EncryptorSettings("1234", new byte[] { 35, 36 }));

		service.saveSettings(data);
		assertTrue(dataFolder.exists());

		assertEquals(TestResources.load(DataServiceTest.class, "expected-settings.json.txt"),
				Files.toString(new File(dataFolder, "settings.json"), StandardCharsets.UTF_8));

		Settings loaded = service.loadSettings();
		assertNotNull(loaded);
		assertEquals(data.getEncryptorSettings().getMasterPasswordVerificationState(),
				loaded.getEncryptorSettings().getMasterPasswordVerificationState());
		assertArrayEquals(data.getEncryptorSettings().getSalt(), loaded.getEncryptorSettings().getSalt());
	}

	private PersistenceService createService() {
		return new PersistenceService(new DataGsonProvider() {
			@Override
			GsonBuilder builder() {
				return super.builder().setPrettyPrinting();
			}
		}, dataDirectory);
	}
}

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import greensopinion.finance.services.TestResources;
import greensopinion.finance.services.encryption.EncryptorSettings;

public class DataServiceTest {
	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File dataFolder;
	private DataService service;

	@Before
	public void before() {
		dataFolder = new File(temporaryFolder.getRoot(), "data");
		service = createService();
	}

	@Test
	public void getDataDirectory() {
		DataService service = new DataService(new Gson());
		assertEquals(DataDirectory.locate(), service.getDataDirectory());
	}

	@Test
	public void readNoFile() {
		assertNotNull(service.load());
	}

	@Test
	public void roundTrip() throws IOException {
		assertFalse(dataFolder.exists());

		Data data = new Data();
		data.setEncryptorSettings(new EncryptorSettings("1234", new byte[] { 35, 36 }));

		service.save(data);
		assertTrue(dataFolder.exists());

		assertEquals(TestResources.load(DataServiceTest.class, "expected-data.json.txt"),
				Files.toString(new File(dataFolder, "data.json"), StandardCharsets.UTF_8));

		Data loaded = service.load();
		assertNotNull(loaded);
		assertEquals(data.getEncryptorSettings().getMasterPasswordVerificationState(),
				loaded.getEncryptorSettings().getMasterPasswordVerificationState());
		assertArrayEquals(data.getEncryptorSettings().getSalt(), loaded.getEncryptorSettings().getSalt());
	}

	private DataService createService() {
		return new DataService(new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create()) {
			@Override
			File getDataDirectory() {
				return dataFolder;
			}
		};
	}
}

package greensopinion.finance.services.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

public class DataDirectoryLocatorTest {

	@Test
	public void getDataDirectory() {
		File file = new DataDirectoryLocator().locate();
		if (isMac()) {
			assertEquals(System.getProperty("user.home") + "/Library/Application Support/GreensOpinionFinance",
					file.getPath());
		} else if (isWindows()) {
			assertEquals(System.getProperty("user.home") + "\\AppData\\Roaming\\GreensOpinionFinance", file.getPath());
		} else {
			fail("Unsupported os: " + System.getProperty("os.name"));
		}
		assertTrue(file.getParentFile().exists());
		assertTrue(file.getParentFile().isDirectory());
	}

	private boolean isMac() {
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}

	private boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().contains("windows");
	}
}

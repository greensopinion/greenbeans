package greensopinion.finance.services.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class DataDirectoryLocatorTest {

	@Test
	public void getDataDirectory() {
		File file = new DataDirectoryLocator().locate();
		assertEquals(System.getProperty("user.home") + "/Library/Application Support/GreensOpinionFinance",
				file.getPath());
		assertTrue(file.getParentFile().exists());
		assertTrue(file.getParentFile().isDirectory());
	}
}

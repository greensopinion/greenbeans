package greensopinion.finance.services.persistence;

import java.io.File;
import java.util.Locale;

class DataDirectoryLocator {
	private static final String APP_DIR = "GreensOpinionFinance";

	public File locate() {
		String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
		if (os.contains("mac")) {
			return new File(System.getProperty("user.home") + "/Library/Application Support/" + APP_DIR);
		} else {
			throw new IllegalStateException(os);
		}
	}
}

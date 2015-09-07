package greensopinion.finance.services.data;

import java.io.File;

class DataDirectoryLocator {
	private static final String APP_DIR = "GreensOpinionFinance";

	public File locate() {
		String os = System.getProperty("os.name").toUpperCase();
		if (os.contains("MAC")) {
			return new File(System.getProperty("user.home") + "/Library/Application Support/" + APP_DIR);
		} else {
			throw new IllegalStateException(os);
		}
	}
}

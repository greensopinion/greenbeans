package greensopinion.finance.services.data;

import java.io.File;

class DataDirectory {
	private static final String APP_DIR = "GreensOpinionFinance";

	public static File locate() {
		String os = System.getProperty("os.name").toUpperCase();
		if (os.contains("MAC")) {
			return new File(System.getProperty("user.home") + "/Library/Application Support/" + APP_DIR);
		} else {
			throw new IllegalStateException(os);
		}
	}

	private DataDirectory() {
		// prevent instantiation
	}
}

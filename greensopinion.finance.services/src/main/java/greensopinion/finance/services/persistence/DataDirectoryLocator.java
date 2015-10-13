package greensopinion.finance.services.persistence;

import java.io.File;
import java.util.Locale;

import greensopinion.finance.services.demo.Demo;

public class DataDirectoryLocator {
	private static final String APP_DIR = "GreensOpinionFinance";

	public File locate() {
		String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
		String path;
		if (os.contains("mac")) {
			path = System.getProperty("user.home") + "/Library/Application Support/" + APP_DIR;

		} else {
			throw new IllegalStateException(os);
		}
		if (Demo.isEnabled()) {
			path += "-demo";
		}
		return new File(path);
	}
}

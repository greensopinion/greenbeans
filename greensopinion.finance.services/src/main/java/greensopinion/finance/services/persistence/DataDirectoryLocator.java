package greensopinion.finance.services.persistence;

import java.io.File;
import java.util.Locale;

import com.google.common.base.Strings;

import greensopinion.finance.services.demo.Demo;

public class DataDirectoryLocator {
	private static final String APP_DIR = "GreensOpinionFinance";

	public File locate() {
		String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
		String path;
		if (os.contains("mac")) {
			path = System.getProperty("user.home") + "/Library/Application Support/" + APP_DIR;
		} else if (os.contains("windows")) {
			String appData = System.getenv("APPDATA");
			if (Strings.isNullOrEmpty(appData)) {
				appData = System.getProperty("user.home") + "AppData\\Roaming";
			}
			path = appData + "\\" + APP_DIR;
		} else {
			throw new IllegalStateException(os);
		}
		if (Demo.isEnabled()) {
			path += "-demo";
		}
		return new File(path);
	}
}

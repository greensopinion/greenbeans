/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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

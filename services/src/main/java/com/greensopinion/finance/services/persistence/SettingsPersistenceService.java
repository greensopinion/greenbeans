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
package com.greensopinion.finance.services.persistence;

import javax.inject.Inject;

import com.greensopinion.finance.services.domain.Settings;

public class SettingsPersistenceService extends PersistenceService<Settings> {

	private static final String FILENAME = "settings.json";

	@Inject
	SettingsPersistenceService(PersistenceGsonProvider gsonProvider, DataDirectoryLocator dataDirectoryLocator) {
		super(gsonProvider, dataDirectoryLocator, Settings.class);
	}

	@Override
	String getFilename() {
		return FILENAME;
	}

	@Override
	Settings defaultInstance() {
		return new Settings();
	}

}

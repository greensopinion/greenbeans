/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.persistence;

import javax.inject.Inject;

import greensopinion.finance.services.domain.Settings;

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

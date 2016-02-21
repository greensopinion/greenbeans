/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.domain;

import javax.inject.Inject;

import greensopinion.finance.services.persistence.ConfigurationService;
import greensopinion.finance.services.persistence.SettingsPersistenceService;

public class SettingsService extends ConfigurationService<Settings> {

	@Inject
	SettingsService(SettingsPersistenceService persistenceService, EntityEventSupport eventSupport) {
		super(persistenceService, eventSupport);
	}
}

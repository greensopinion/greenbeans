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

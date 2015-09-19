package greensopinion.finance.services.data;

import javax.inject.Inject;

import greensopinion.finance.services.domain.Settings;

public class SettingsService extends ConfigurationService<Settings> {

	@Inject
	SettingsService(SettingsPersistenceService persistenceService) {
		super(persistenceService);
	}

}

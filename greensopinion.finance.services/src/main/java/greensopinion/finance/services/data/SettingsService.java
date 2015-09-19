package greensopinion.finance.services.data;

import javax.inject.Inject;

public class SettingsService extends ConfigurationService<Settings> {

	@Inject
	SettingsService(SettingsPersistenceService persistenceService) {
		super(persistenceService);
	}

}

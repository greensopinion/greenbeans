package greensopinion.finance.services.data;

import javax.inject.Inject;

class SettingsPersistenceService extends PersistenceService<Settings> {

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

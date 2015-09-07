package greensopinion.finance.services.data;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import greensopinion.finance.services.encryption.EncryptorSettings;

public class ConfigurationService {
	private final DataService dataService;
	private final Object dataLock = new Object();
	private Data data;

	@Inject
	ConfigurationService(DataService dataService) {
		this.dataService = checkNotNull(dataService);
	}

	public EncryptorSettings getEncryptorSettings() {
		return data().getEncryptorSettings();
	}

	public void setEncryptorSettings(EncryptorSettings encryptorSettings) {
		synchronized (dataLock) {
			Data copy = new Data();
			copy.setEncryptorSettings(encryptorSettings);
			dataService.save(copy);
			this.data = copy;
		}
	}

	Data data() {
		synchronized (dataLock) {
			if (data == null) {
				data = dataService.load();
			}
			return data;
		}
	}
}

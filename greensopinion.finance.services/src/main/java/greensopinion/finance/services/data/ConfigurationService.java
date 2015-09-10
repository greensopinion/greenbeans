package greensopinion.finance.services.data;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Inject;

import greensopinion.finance.services.encryption.EncryptorSettings;
import greensopinion.finance.services.transaction.Transaction;

public class ConfigurationService {
	private final PersistenceService persistenceService;
	private final Object dataLock = new Object();
	private Settings data;

	@Inject
	ConfigurationService(PersistenceService persistenceService) {
		this.persistenceService = checkNotNull(persistenceService);
	}

	public EncryptorSettings getEncryptorSettings() {
		return data().getEncryptorSettings();
	}

	public void setEncryptorSettings(EncryptorSettings encryptorSettings) {
		synchronized (dataLock) {
			Settings copy = createCopy(data());
			copy.setEncryptorSettings(encryptorSettings);
			persistenceService.saveSettings(copy);
			this.data = copy;
		}
	}

	private Settings createCopy(Settings original) {
		Settings copy = new Settings();
		copy.setEncryptorSettings(original.getEncryptorSettings());
		return copy;
	}

	public void addTransactions(List<Transaction> transactions) {
		throw new UnsupportedOperationException();
	}

	Settings data() {
		synchronized (dataLock) {
			if (data == null) {
				data = persistenceService.loadSettings();
			}
			return data;
		}
	}
}

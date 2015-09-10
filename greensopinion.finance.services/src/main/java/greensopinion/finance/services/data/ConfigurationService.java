package greensopinion.finance.services.data;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import greensopinion.finance.services.encryption.EncryptorSettings;

public class ConfigurationService {
	private final PersistenceService persistenceService;
	private final Object dataLock = new Object();
	private Settings data;

	private Transactions transactions;

	@Inject
	ConfigurationService(PersistenceService persistenceService) {
		this.persistenceService = checkNotNull(persistenceService);
	}

	public EncryptorSettings getEncryptorSettings() {
		return settings().getEncryptorSettings();
	}

	public void setEncryptorSettings(EncryptorSettings encryptorSettings) {
		synchronized (dataLock) {
			Settings copy = createCopy(settings());
			copy.setEncryptorSettings(encryptorSettings);
			persistenceService.saveSettings(copy);
			this.data = copy;
		}
	}

	public Transactions getTransactions() {
		synchronized (dataLock) {
			if (transactions == null) {
				transactions = persistenceService.loadTransactions();
			}
			return transactions;
		}
	}

	public void setTransactions(Transactions transactions) {
		synchronized (dataLock) {
			persistenceService.saveTransactions(transactions);
			this.transactions = transactions;
		}
	}

	private Settings createCopy(Settings original) {
		Settings copy = new Settings();
		copy.setEncryptorSettings(original.getEncryptorSettings());
		return copy;
	}

	Settings settings() {
		synchronized (dataLock) {
			if (data == null) {
				data = persistenceService.loadSettings();
			}
			return data;
		}
	}
}

package greensopinion.finance.services.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

public class ConfigurationService<T> {
	private final PersistenceService<T> persistenceService;
	private final Object dataLock = new Object();
	private T data;

	public ConfigurationService(PersistenceService<T> persistenceService) {
		this.persistenceService = checkNotNull(persistenceService);
	}

	public T retrieve() {
		synchronized (dataLock) {
			if (data == null) {
				data = load();
			}
			return data;
		}
	}

	public void update(T value) {
		checkNotNull(value);
		synchronized (dataLock) {
			persistenceService.save(value);
			data = value;
		}
	}

	protected T load() {
		return persistenceService.load();
	}
}

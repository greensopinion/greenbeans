package greensopinion.finance.services.data;

import static com.google.common.base.Preconditions.checkNotNull;

public class ConfigurationService<T> {
	private final PersistenceService<T> persistenceService;
	private final Object dataLock = new Object();
	private T data;

	ConfigurationService(PersistenceService<T> persistenceService) {
		this.persistenceService = checkNotNull(persistenceService);
	}

	public T retrieve() {
		synchronized (dataLock) {
			if (data == null) {
				data = persistenceService.load();
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
}

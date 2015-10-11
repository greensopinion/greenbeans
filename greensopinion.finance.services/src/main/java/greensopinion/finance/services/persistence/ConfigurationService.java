package greensopinion.finance.services.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import greensopinion.finance.services.domain.EntityEventSupport;

public class ConfigurationService<T> {
	private final PersistenceService<T> persistenceService;
	private final EntityEventSupport eventSupport;

	private final Object dataLock = new Object();
	private T data;

	public ConfigurationService(PersistenceService<T> persistenceService, EntityEventSupport eventSupport) {
		this.persistenceService = checkNotNull(persistenceService);
		this.eventSupport = checkNotNull(eventSupport);
	}

	public T retrieve() {
		synchronized (dataLock) {
			if (data == null) {
				data = load();
			}
			return data;
		}
	}

	protected void clearState() {
		synchronized (dataLock) {
			data = null;
		}
	}

	public void update(T value) {
		checkNotNull(value);
		synchronized (dataLock) {
			persistenceService.save(value);
			data = value;
		}
		eventSupport.updated(value);
	}

	protected T load() {
		return persistenceService.load();
	}
}

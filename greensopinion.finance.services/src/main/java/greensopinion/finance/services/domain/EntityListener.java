package greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class EntityListener<T> {
	private final Class<T> type;

	public EntityListener(Class<T> type) {
		this.type = checkNotNull(type);
	}

	public Class<T> type() {
		return type;
	}

	public abstract void updated(T entity);
}

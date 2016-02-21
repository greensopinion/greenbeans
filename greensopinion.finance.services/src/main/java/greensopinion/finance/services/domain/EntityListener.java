/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
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

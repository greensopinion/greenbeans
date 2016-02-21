/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class EntityEventSupport {
	private List<EntityListener<?>> listeners = ImmutableList.of();

	public void addListener(EntityListener<?> listener) {
		checkNotNull(listener);
		listeners = ImmutableList.<EntityListener<?>> builder().addAll(listeners).add(listener).build();
	}

	public void updated(Object entity) {
		checkNotNull(entity);
		for (EntityListener<?> listener : listeners) {
			updated(listener, entity);
		}
	}

	private <T> void updated(EntityListener<T> listener, Object entity) {
		Class<T> type = listener.type();
		if (type.isAssignableFrom(entity.getClass())) {
			listener.updated(type.cast(entity));
		}
	}
}

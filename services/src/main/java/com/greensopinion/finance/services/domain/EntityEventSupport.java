/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.greensopinion.finance.services.domain;

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

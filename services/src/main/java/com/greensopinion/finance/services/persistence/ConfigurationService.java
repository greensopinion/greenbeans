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
package com.greensopinion.finance.services.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import com.greensopinion.finance.services.domain.EntityEventSupport;

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

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.greensopinion.finance.services.domain.Categories;
import com.greensopinion.finance.services.domain.EntityEventSupport;
import com.greensopinion.finance.services.domain.EntityListener;
import com.greensopinion.finance.services.domain.Transactions;

public class EntityEventSupportTest {
	static class TestListener<T> extends EntityListener<T> {

		private final List<T> updated = new ArrayList<>();

		public TestListener(Class<T> type) {
			super(type);
		}

		@Override
		public void updated(T entity) {
			updated.add(entity);
		}
	}

	@Test
	public void udpated() {
		EntityEventSupport support = new EntityEventSupport();

		TestListener<Categories> listener1 = new TestListener<>(Categories.class);
		TestListener<Transactions> listener2 = new TestListener<>(Transactions.class);
		support.addListener(listener1);
		support.addListener(listener2);

		support.updated(new Object());
		assertTrue(listener1.updated.isEmpty());
		assertTrue(listener2.updated.isEmpty());

		Categories categories = new Categories();
		support.updated(categories);

		assertEquals(ImmutableList.of(categories), listener1.updated);
		assertTrue(listener2.updated.isEmpty());
	}
}

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
package greensopinion.finance.services.web.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Ordering;

public class CategoryModelTest {

	@Test
	public void compareTo() {
		CategoryModel m1 = new CategoryModel("a");
		CategoryModel m2 = new CategoryModel("B");
		CategoryModel m3 = new CategoryModel("c");
		assertOrder(m1, m2, m3);
		assertEquals(0, m1.compareTo(m1));
		assertCompareOrder(m1, m2);
		assertCompareOrder(m2, m3);
	}

	private void assertCompareOrder(CategoryModel m1, CategoryModel m2) {
		assertEquals(0, m1.compareTo(m1));
		assertEquals(0, m2.compareTo(m2));
		assertEquals(1, m2.compareTo(m1));
		assertEquals(-1, m1.compareTo(m2));
	}

	private void assertOrder(CategoryModel... models) {
		assertEquals(Arrays.asList(models),
				FluentIterable.from(Arrays.asList(models)).toSortedList(Ordering.natural()));
	}
}

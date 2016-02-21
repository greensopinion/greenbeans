/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
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

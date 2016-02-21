/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.transaction;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class EntityReferencesTest {

	@Test
	public void nameToEntityReferences() {
		assertNameToEntityReferences("#38", "amp");
		assertNameToEntityReferences("#8822", "LessGreater");
	}

	@Test
	public void namedEntityToString() {
		assertNamedEntityToString("&", "amp");
		assertNamedEntityToString("≶", "LessGreater");
		assertNamedEntityToString("Ã", "#195");
	}

	private void assertNamedEntityToString(String expected, String named) {
		assertEquals(expected, EntityReferences.instance().namedEntityToString(named));
	}

	private void assertNameToEntityReferences(String expected, String named) {
		List<String> references = EntityReferences.instance().nameToEntityReferences(named);
		assertEquals(ImmutableList.of(expected), references);
	}
}

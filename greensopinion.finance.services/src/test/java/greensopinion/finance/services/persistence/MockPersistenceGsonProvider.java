/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.persistence;

import com.google.gson.GsonBuilder;

import greensopinion.finance.services.persistence.PersistenceGsonProvider;

public class MockPersistenceGsonProvider {
	public static PersistenceGsonProvider create() {
		return new PersistenceGsonProvider(MockEncryptorProviderService.create()) {
			@Override
			GsonBuilder builder() {
				return super.builder().setPrettyPrinting();
			}
		};
	}
}

/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.encryption;

import static greensopinion.finance.services.InjectorAsserts.assertSingletonBinding;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class EncryptionModuleTest {
	@Test
	public void providesEncryptorProviderService() {
		assertSingletonBinding(createInjector(), EncryptorProviderService.class);
	}

	@Test
	public void providesEncryptorService() {
		assertSingletonBinding(createInjector(), EncryptorService.class);
	}

	@Test
	public void providesMasterPasswordChangeSupport() {
		assertSingletonBinding(createInjector(), EncryptorListener.class, MasterPasswordChangeSupport.class);
	}

	private Injector createInjector() {
		return Guice.createInjector(new EncryptionModule());
	}
}

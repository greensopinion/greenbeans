/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.encryption;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class EncryptionModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EncryptorProviderService.class).in(Scopes.SINGLETON);
		bind(EncryptorService.class).in(Scopes.SINGLETON);
		bind(EncryptorListener.class).to(MasterPasswordChangeSupport.class).in(Scopes.SINGLETON);
	}
}

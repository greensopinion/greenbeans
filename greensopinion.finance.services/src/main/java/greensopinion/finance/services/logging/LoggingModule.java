/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.logging;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class LoggingModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(LogConfigurator.class).in(Scopes.SINGLETON);
	}
}

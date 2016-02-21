/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.application;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import greensopinion.finance.services.bridge.WebInvoker;

public class ServiceLocatorTest {

	@Test
	public void create() {
		WebInvoker invoker = mock(WebInvoker.class);
		ServiceLocator serviceLocator = new ServiceLocator(invoker);
		assertSame(invoker, serviceLocator.getWebInvoker());
	}
}

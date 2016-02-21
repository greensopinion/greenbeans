/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.application;

import static greensopinion.finance.services.InjectorAsserts.assertSingletonBinding;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import greensopinion.finance.services.bridge.WebInvoker;
import javafx.application.Application.Parameters;
import javafx.embed.swing.JFXPanel;

public class SceneModuleTest {
	private final Parameters parameters = mock(Parameters.class);

	@Before
	public void initializeJavaFXRuntime() {
		new JFXPanel();
	}

	@Test
	public void providesMainScene() {
		assertSingletonBinding(createInjector(), MainScene.class);
	}

	@Test
	public void providesWebApplicationRegion() {
		assertSingletonBinding(createInjector(), WebApplicationRegion.class);
	}

	@Test
	public void providesParameters() {
		Parameters params = assertSingletonBinding(createInjector(), Parameters.class);
		assertSame(parameters, params);
	}

	@Test
	public void providesServiceLocator() {
		assertSingletonBinding(createInjector(), ServiceLocator.class);
	}

	private Injector createInjector() {
		return Guice.createInjector(new SceneModule(parameters), new AbstractModule() {
			@Override
			protected void configure() {
				bind(WebInvoker.class).toInstance(mock(WebInvoker.class));
			}
		});
	}
}

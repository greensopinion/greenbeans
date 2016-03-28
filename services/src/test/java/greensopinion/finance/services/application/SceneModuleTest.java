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

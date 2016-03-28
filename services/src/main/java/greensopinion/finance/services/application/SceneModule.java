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

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import javafx.application.Application.Parameters;

class SceneModule extends AbstractModule {

	private final Parameters parameters;

	public SceneModule(Parameters parameters) {
		this.parameters = checkNotNull(parameters);
	}

	@Override
	protected void configure() {
		bind(Parameters.class).toInstance(parameters);
		bind(MainScene.class).in(Scopes.SINGLETON);
		bind(WebApplicationRegion.class).in(Scopes.SINGLETON);
		bind(ServiceLocator.class).in(Scopes.SINGLETON);
	}
}

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
package com.greensopinion.finance.services.persistence;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import com.google.common.base.Throwables;
import com.greensopinion.finance.services.domain.Categories;

public class CategoriesPersistenceService extends PersistenceService<Categories> {

	private static final String FILENAME = "categories.json";

	@Inject
	CategoriesPersistenceService(PersistenceGsonProvider gsonProvider, DataDirectoryLocator dataDirectoryLocator) {
		super(gsonProvider, dataDirectoryLocator, Categories.class);
	}

	@Override
	String getFilename() {
		return FILENAME;
	}

	@Override
	Categories defaultInstance() {
		try (InputStream stream = CategoriesPersistenceService.class.getResourceAsStream("default-categories.json")) {
			return read(stream);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
}

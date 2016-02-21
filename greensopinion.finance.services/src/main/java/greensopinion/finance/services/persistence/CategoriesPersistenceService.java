/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.persistence;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import com.google.common.base.Throwables;

import greensopinion.finance.services.domain.Categories;

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

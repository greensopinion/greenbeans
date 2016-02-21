/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.model;

import java.util.List;

public class FileImport {
	private boolean deleteAfterImport;
	private List<String> files;

	public List<String> getFiles() {
		return files;
	}

	public boolean isDeleteAfterImport() {
		return deleteAfterImport;
	}
}

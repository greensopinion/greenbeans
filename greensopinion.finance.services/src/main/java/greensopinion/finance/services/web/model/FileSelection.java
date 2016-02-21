/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.model;

import java.util.List;

public class FileSelection {
	private final List<String> files;

	public FileSelection(List<String> files) {
		this.files = files;
	}

	public List<String> getFiles() {
		return files;
	}
}

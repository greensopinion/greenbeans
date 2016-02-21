/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.model;

public class About {
	private String applicationName;
	private String copyrightNotice;

	public About() {
	}

	public About(String applicationName, String copyrightNotice) {
		this.applicationName = applicationName;
		this.copyrightNotice = copyrightNotice;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public String getCopyrightNotice() {
		return copyrightNotice;
	}
}

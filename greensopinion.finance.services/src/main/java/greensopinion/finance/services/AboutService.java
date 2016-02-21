/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services;

import greensopinion.finance.services.web.model.About;

public class AboutService {

	public About getAbout() {
		return new About(getApplicationName(), getCopyrightNotice());
	}

	private String getApplicationName() {
		return GreenBeans.APP_NAME;
	}

	private String getCopyrightNotice() {
		return "Copyright (c) 2015, 2016 David Green. All rights reserved.";
	}
}

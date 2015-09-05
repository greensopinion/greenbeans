package greensopinion.finance.services;

import greensopinion.finance.services.model.About;

public class AboutService {

	public About getAbout() {
		return new About(getApplicationName(), getCopyrightNotice());
	}

	private String getApplicationName() {
		return "Green's Opinion - Finances";
	}

	private String getCopyrightNotice() {
		return "Copyright (c) 2015 David Green.  All rights reserved.";
	}
}

/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.model;

public class UserEulaStatus {

	private final boolean userHasAgreedToLicense;

	public UserEulaStatus(boolean userHasAgreedToLicense) {
		this.userHasAgreedToLicense = userHasAgreedToLicense;
	}

	public boolean userHasAgreedToLicense() {
		return userHasAgreedToLicense;
	}
}

/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.encryption;

import greensopinion.finance.services.ValidationException;

public class InvalidMasterPasswordException extends ValidationException {
	private static final long serialVersionUID = 1L;

	public InvalidMasterPasswordException() {
		super("Invalid master password.  Please try again.");
	}
}

/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.transaction;

public class InvalidTransactionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidTransactionException(String message, Throwable cause) {
		super(message, cause);
	}

}

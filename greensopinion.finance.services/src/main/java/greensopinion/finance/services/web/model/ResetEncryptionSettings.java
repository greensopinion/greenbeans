/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.model;

public class ResetEncryptionSettings {
	private String masterPassword;
	private String newMasterPassword;

	public ResetEncryptionSettings() {
	}

	public ResetEncryptionSettings(String masterPassword, String newMasterPassword) {
		this.masterPassword = masterPassword;
		this.newMasterPassword = newMasterPassword;
	}

	public String getMasterPassword() {
		return masterPassword;
	}

	public String getNewMasterPassword() {
		return newMasterPassword;
	}
}

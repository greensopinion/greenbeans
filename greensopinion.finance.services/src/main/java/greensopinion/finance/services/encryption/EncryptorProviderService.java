/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.encryption;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.atomic.AtomicReference;

public class EncryptorProviderService {

	private final AtomicReference<Encryptor> encryptorReference = new AtomicReference<>();

	public boolean isInitialized() {
		return encryptorReference.get() != null;
	}

	public Encryptor getEncryptor() {
		return checkNotNull(encryptorReference.get(), "Encryptor is not initialized");
	}

	public void setEncryptor(Encryptor encryptor) {
		checkNotNull(encryptor, "Must provide an encryptor");
		encryptorReference.set(encryptor);
	}
}

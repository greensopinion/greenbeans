/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.greensopinion.finance.services.encryption;

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

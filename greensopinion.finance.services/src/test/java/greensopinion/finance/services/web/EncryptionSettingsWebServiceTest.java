/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import greensopinion.finance.services.encryption.EncryptorService;
import greensopinion.finance.services.web.model.ResetEncryptionSettings;

public class EncryptionSettingsWebServiceTest {
	private final EncryptorService encryptorService = mock(EncryptorService.class);
	private final EncryptionSettingsWebService service = new EncryptionSettingsWebService(encryptorService);

	@Test
	public void reset() {
		service.configure(new ResetEncryptionSettings("one", "two"));
		verify(encryptorService).initialize("one");
		verify(encryptorService).reconfigure("two");
	}
}

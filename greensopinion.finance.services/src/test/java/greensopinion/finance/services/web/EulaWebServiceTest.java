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
package greensopinion.finance.services.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Strings;

import greensopinion.finance.services.ValidationException;
import greensopinion.finance.services.domain.Settings;
import greensopinion.finance.services.domain.SettingsService;
import greensopinion.finance.services.web.model.Eula;
import greensopinion.finance.services.web.model.UserEulaStatus;

public class EulaWebServiceTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	private final SettingsService settingsService = mock(SettingsService.class);
	private final EulaWebService service = new EulaWebService(settingsService);

	@Test
	public void current() {
		Eula eula = service.current();
		assertNotNull(eula);
		assertFalse(Strings.isNullOrEmpty(eula.getText()));
		assertTrue(eula.getText().contains("END USER LICENSE AGREEMENT"));
	}

	@Test
	public void getCurrentUserEulaStatus() {
		doReturn(new Settings()).when(settingsService).retrieve();
		UserEulaStatus current = service.currentUserEulaStatus();
		assertFalse(current.userHasAgreedToLicense());

		doReturn(new Settings().withUserHasAgreedToLicense(true)).when(settingsService).retrieve();
		current = service.currentUserEulaStatus();
		assertTrue(current.userHasAgreedToLicense());
	}

	@Test
	public void putCurrentUserEulaStatus() {
		doReturn(new Settings()).when(settingsService).retrieve();

		service.currentUserEulaStatus(new UserEulaStatus(true));
		ArgumentCaptor<Settings> settingsCaptor = ArgumentCaptor.forClass(Settings.class);
		verify(settingsService).update(settingsCaptor.capture());

		Settings settings = settingsCaptor.getValue();
		assertNotNull(settings);
		assertEquals(true, settings.userHasAgreedToLicense());
	}

	@Test
	public void putCurrentUserEulaStatusNotAccepted() {
		doReturn(new Settings()).when(settingsService).retrieve();

		thrown.expect(ValidationException.class);
		thrown.expectMessage("You must agree to the license in order to use Green Beans.");
		service.currentUserEulaStatus(new UserEulaStatus(false));
	}
}

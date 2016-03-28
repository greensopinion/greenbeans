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
package greensopinion.finance.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

import org.junit.Test;

import greensopinion.finance.services.web.model.About;

public class AboutServiceTest {

	private final AboutService service = new AboutService();

	@Test
	public void getAbout() {
		About about = service.getAbout();
		assertNotNull(about);
		assertEquals("Green Beans", about.getApplicationName());
		int currentYear = LocalDate.now().get(ChronoField.YEAR);
		assertEquals("Copyright (c) 2015, " + currentYear + " David Green. All rights reserved.",
				about.getCopyrightNotice());
	}
}

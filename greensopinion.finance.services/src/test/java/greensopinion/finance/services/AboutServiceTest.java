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

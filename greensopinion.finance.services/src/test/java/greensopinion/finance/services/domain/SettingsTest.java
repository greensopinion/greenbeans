package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import org.junit.Test;

public class SettingsTest {

	@Test
	public void defaults() {
		Settings settings = new Settings();
		assertFalse(settings.userHasAgreedToLicense());
		assertEquals(null, settings.getEncryptorSettings());
	}

	@Test
	public void userHasAgreedToLicense() {
		Settings settings = new Settings(null, false);
		assertEquals(false, settings.userHasAgreedToLicense());
		settings = settings.withUserHasAgreedToLicense(true);
		assertEquals(true, settings.userHasAgreedToLicense());
	}

	@Test
	public void encryptorSettings() {
		Settings settings = new Settings(null, false);
		assertEquals(false, settings.userHasAgreedToLicense());
		EncryptorSettings encryptorSettings = mock(EncryptorSettings.class);
		settings = settings.withUserHasAgreedToLicense(true).withEncryptorSettings(encryptorSettings);
		assertEquals(true, settings.userHasAgreedToLicense());
		assertSame(encryptorSettings, settings.getEncryptorSettings());
	}
}

package greensopinion.finance.services.data;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;

import greensopinion.finance.services.encryption.EncryptorSettings;

public class ConfigurationServiceTest {

	private PersistenceService persistenceService;
	private ConfigurationService configurationService;
	private Settings data;

	@Before
	public void before() {
		data = new Settings();
		persistenceService = mock(PersistenceService.class);
		doReturn(data).when(persistenceService).loadSettings();

		configurationService = new ConfigurationService(persistenceService);
	}

	@Test
	public void getEncryptorSettings() {
		EncryptorSettings encryptorSettings = EncryptorSettings.newSettings("123");
		data.setEncryptorSettings(encryptorSettings);

		verifyNoMoreInteractions(persistenceService);
		assertSame(encryptorSettings, configurationService.getEncryptorSettings());
		verify(persistenceService).loadSettings();
		assertSame(encryptorSettings, configurationService.getEncryptorSettings());
		verifyNoMoreInteractions(persistenceService);
	}

	@Test
	public void setEncryptorSettings() {
		EncryptorSettings encryptorSettings = EncryptorSettings.newSettings("123");

		verifyNoMoreInteractions(persistenceService);
		configurationService.setEncryptorSettings(encryptorSettings);
		verify(persistenceService).loadSettings();
		verify(persistenceService).saveSettings(any(Settings.class));

		assertNull(data.getEncryptorSettings());
		verifyNoMoreInteractions(persistenceService);
		assertSame(encryptorSettings, configurationService.getEncryptorSettings());
		assertNotSame(data, configurationService.data());
	}
}

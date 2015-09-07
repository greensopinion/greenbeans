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

	private DataService dataService;
	private ConfigurationService configurationService;
	private Data data;

	@Before
	public void before() {
		data = new Data();
		dataService = mock(DataService.class);
		doReturn(data).when(dataService).load();

		configurationService = new ConfigurationService(dataService);
	}

	@Test
	public void getEncryptorSettings() {
		EncryptorSettings encryptorSettings = EncryptorSettings.newSettings("123");
		data.setEncryptorSettings(encryptorSettings);

		verifyNoMoreInteractions(dataService);
		assertSame(encryptorSettings, configurationService.getEncryptorSettings());
		verify(dataService).load();
		assertSame(encryptorSettings, configurationService.getEncryptorSettings());
		verifyNoMoreInteractions(dataService);
	}

	@Test
	public void setEncryptorSettings() {
		EncryptorSettings encryptorSettings = EncryptorSettings.newSettings("123");

		verifyNoMoreInteractions(dataService);
		configurationService.setEncryptorSettings(encryptorSettings);
		verify(dataService).save(any(Data.class));

		assertNull(data.getEncryptorSettings());
		verifyNoMoreInteractions(dataService);
		assertSame(encryptorSettings, configurationService.getEncryptorSettings());
		assertNotSame(data, configurationService.data());
	}
}

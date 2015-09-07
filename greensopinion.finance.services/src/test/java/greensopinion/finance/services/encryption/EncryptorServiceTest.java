package greensopinion.finance.services.encryption;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import greensopinion.finance.services.data.ConfigurationService;

public class EncryptorServiceTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	private ConfigurationService configurationService;
	private EncryptorProviderService encryptorProviderService;
	private EncryptorService encryptorService;

	@Before
	public void before() {
		configurationService = mock(ConfigurationService.class);
		encryptorProviderService = spy(new EncryptorProviderService());
		encryptorService = new EncryptorService(configurationService, encryptorProviderService);
	}

	@Test
	public void isInitialized() {
		assertFalse(encryptorService.isInitialized());
		encryptorProviderService.setEncryptor(mock(Encryptor.class));
		assertTrue(encryptorService.isInitialized());
	}

	@Test
	public void isConfigured() {
		assertFalse(encryptorService.isConfigured());
		doReturn(mock(EncryptorSettings.class)).when(configurationService).getEncryptorSettings();
		assertTrue(encryptorService.isConfigured());
	}

	@Test
	public void configure() {
		encryptorService.configure("1234");
		ArgumentCaptor<EncryptorSettings> settingsCaptor = ArgumentCaptor.forClass(EncryptorSettings.class);

		InOrder order = inOrder(encryptorProviderService, configurationService);
		order.verify(encryptorProviderService).setEncryptor(any(Encryptor.class));
		order.verify(configurationService).setEncryptorSettings(settingsCaptor.capture());

		EncryptorSettings settings = settingsCaptor.getValue();
		assertTrue(settings.validateMasterPassword("1234"));
	}

	@Test
	public void configureNull() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Must provide a master password");
		encryptorService.configure(null);
	}

	@Test
	public void configureEmpty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Must provide a master password");
		encryptorService.configure("");
	}

	@Test
	public void configureInvalid() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Master password must not have leading or trailing whitespace");
		encryptorService.configure(" asdf");
	}

	@Test
	public void initializeWrongPassword() {
		EncryptorSettings encryptorSettings = EncryptorSettings.newSettings("1234");
		doReturn(encryptorSettings).when(configurationService).getEncryptorSettings();

		thrown.expect(InvalidMasterPasswordException.class);
		encryptorService.initialize("not-the-same");
	}

	@Test
	public void initialize() {
		EncryptorSettings encryptorSettings = EncryptorSettings.newSettings("1234");
		doReturn(encryptorSettings).when(configurationService).getEncryptorSettings();

		assertFalse(encryptorService.isInitialized());
		encryptorService.initialize("1234");
		verify(encryptorProviderService).setEncryptor(any(Encryptor.class));
		assertTrue(encryptorService.isInitialized());
	}
}

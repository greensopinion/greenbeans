package greensopinion.finance.services.encryption;

import static com.google.common.base.MoreObjects.firstNonNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InOrder;

import greensopinion.finance.services.domain.EncryptorSettings;
import greensopinion.finance.services.domain.Settings;
import greensopinion.finance.services.domain.SettingsService;

public class EncryptorServiceTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	private SettingsService settingsService;
	private EncryptorProviderService encryptorProviderService;
	private EncryptorService encryptorService;
	private final EncryptorListener encryptorListener = mock(EncryptorListener.class);
	private final AtomicReference<Settings> settings = new AtomicReference<>();

	@Before
	public void before() {
		settingsService = mockSettingsService();
		encryptorProviderService = spy(new EncryptorProviderService());
		encryptorService = new EncryptorService(settingsService, encryptorProviderService, encryptorListener);
	}

	private SettingsService mockSettingsService() {
		SettingsService settingsService = mock(SettingsService.class);
		doAnswer(i -> firstNonNull(settings.get(), new Settings())).when(settingsService).retrieve();
		doAnswer(i -> {
			settings.set(i.getArgumentAt(0, Settings.class));
			return null;
		}).when(settingsService).update(any());
		return settingsService;
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
		settings.set(new Settings(mock(EncryptorSettings.class), false));
		assertTrue(encryptorService.isConfigured());
	}

	@Test
	public void configure() {
		encryptorService.configure("1234");

		InOrder order = inOrder(encryptorProviderService, settingsService);
		order.verify(encryptorProviderService).setEncryptor(any(Encryptor.class));
		order.verify(settingsService).update(any());

		assertTrue(settings.get().getEncryptorSettings().validateMasterPassword("1234"));
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
		settings.set(new Settings(encryptorSettings, false));

		thrown.expect(InvalidMasterPasswordException.class);
		encryptorService.initialize("not-the-same");
	}

	@Test
	public void initialize() {
		EncryptorSettings encryptorSettings = EncryptorSettings.newSettings("1234");
		settings.set(new Settings(encryptorSettings, false));

		assertFalse(encryptorService.isInitialized());
		encryptorService.initialize("1234");
		verify(encryptorProviderService).setEncryptor(any(Encryptor.class));
		assertTrue(encryptorService.isInitialized());
	}

	@Test
	public void reconfigure() {
		encryptorService.configure("1234");

		InOrder order = inOrder(encryptorProviderService, settingsService);
		order.verify(encryptorProviderService).setEncryptor(any(Encryptor.class));
		order.verify(settingsService).update(any());

		assertTrue(settings.get().getEncryptorSettings().validateMasterPassword("1234"));

		encryptorService.reconfigure("5678");

		order = inOrder(encryptorProviderService, settingsService, encryptorListener);
		order.verify(encryptorListener).aboutToChangeEncryptor();
		order.verify(encryptorProviderService).setEncryptor(any(Encryptor.class));
		order.verify(settingsService).update(any());
		order.verify(encryptorListener).encryptorChanged();

		assertTrue(settings.get().getEncryptorSettings().validateMasterPassword("5678"));
	}

	@Test
	public void reconfigureBeforeInitialize() {
		EncryptorSettings encryptorSettings = EncryptorSettings.newSettings("1234");
		settings.set(new Settings(encryptorSettings, false));

		thrown.expect(IllegalStateException.class);
		thrown.expectMessage("Encryption must be initialized to reset the master password");
		encryptorService.reconfigure("5678");
	}

	@Test
	public void reconfigureNull() {
		thrown.expect(NullPointerException.class);
		thrown.expectMessage("Must provide a master password");
		encryptorService.reconfigure(null);
	}

	@Test
	public void reconfigureEmpty() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Must provide a master password");
		encryptorService.reconfigure("");
	}

	@Test
	public void reconfigureInvalid() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Master password must not have leading or trailing whitespace");
		encryptorService.reconfigure(" asdf");
	}
}

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

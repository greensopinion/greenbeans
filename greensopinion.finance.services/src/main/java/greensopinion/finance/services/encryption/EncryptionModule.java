package greensopinion.finance.services.encryption;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class EncryptionModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EncryptorProviderService.class).in(Scopes.SINGLETON);
		bind(EncryptorService.class).in(Scopes.SINGLETON);
	}
}

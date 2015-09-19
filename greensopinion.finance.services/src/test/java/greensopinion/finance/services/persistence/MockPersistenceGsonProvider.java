package greensopinion.finance.services.persistence;

import com.google.gson.GsonBuilder;

import greensopinion.finance.services.persistence.PersistenceGsonProvider;

public class MockPersistenceGsonProvider {
	public static PersistenceGsonProvider create() {
		return new PersistenceGsonProvider(MockEncryptorProviderService.create()) {
			@Override
			GsonBuilder builder() {
				return super.builder().setPrettyPrinting();
			}
		};
	}
}

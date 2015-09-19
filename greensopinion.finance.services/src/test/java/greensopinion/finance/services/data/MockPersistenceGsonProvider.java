package greensopinion.finance.services.data;

import com.google.gson.GsonBuilder;

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

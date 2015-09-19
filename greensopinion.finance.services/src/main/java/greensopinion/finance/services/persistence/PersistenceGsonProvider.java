package greensopinion.finance.services.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import greensopinion.finance.services.encryption.EncryptorProviderService;

class PersistenceGsonProvider {
	private final EncryptorProviderService encryptorProviderService;

	@Inject
	PersistenceGsonProvider(EncryptorProviderService encryptorProviderService) {
		this.encryptorProviderService = checkNotNull(encryptorProviderService);
	}

	public Gson get() {
		GsonBuilder gsonBuilder = builder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
		gsonBuilder.registerTypeAdapterFactory(TransactionsTypeAdapter.factory(encryptorProviderService));
		return gsonBuilder.create();
	}

	GsonBuilder builder() {
		return new GsonBuilder().disableHtmlEscaping();
	}
}

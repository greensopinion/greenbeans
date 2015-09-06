package greensopinion.finance.services.encryption;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.concurrent.atomic.AtomicReference;

public class EncryptorProviderService {

	private AtomicReference<Encryptor> encryptorReference = new AtomicReference<>();

	public boolean isInitialized() {
		return encryptorReference.get() != null;
	}

	public Encryptor getEncryptor() {
		return checkNotNull(encryptorReference.get(), "Encryptor is not initialized");
	}

	public void setEncryptor(Encryptor encryptor) {
		checkNotNull(encryptor, "Must provide an encryptor");
		checkState(encryptorReference.compareAndSet(null, encryptor), "Cannot initialize encryptor more than once");
	}
}

package greensopinion.finance.services.web.model;

public class EncryptionSettings {

	private final boolean configured;
	private final boolean initialized;

	public EncryptionSettings(boolean configured, boolean initialized) {
		this.configured = configured;
		this.initialized = initialized;
	}

	public boolean isConfigured() {
		return configured;
	}

	public boolean isInitialized() {
		return initialized;
	}
}

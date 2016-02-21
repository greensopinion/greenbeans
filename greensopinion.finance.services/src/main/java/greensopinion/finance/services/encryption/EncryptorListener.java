package greensopinion.finance.services.encryption;

interface EncryptorListener {

	void aboutToChangeEncryptor();

	void encryptorChanged();
}

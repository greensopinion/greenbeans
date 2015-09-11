package greensopinion.finance.services.data;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

import com.google.common.base.Throwables;
import com.google.gson.Gson;

class PersistenceService {

	private static final String SETTINGS_FILE = "settings.json";
	private static final String TRANSACTIONS_FILE = "transactions.json";
	private final PersistenceGsonProvider gsonProvider;
	private final DataDirectoryLocator dataDirectoryLocator;

	@Inject
	PersistenceService(PersistenceGsonProvider gsonProvider, DataDirectoryLocator dataDirectoryLocator) {
		this.gsonProvider = checkNotNull(gsonProvider);
		this.dataDirectoryLocator = checkNotNull(dataDirectoryLocator);
	}

	public Settings loadSettings() {
		File settingsFile = getSettingsFile();
		if (settingsFile.exists()) {
			return read(settingsFile, Settings.class);
		}
		return new Settings();
	}

	public Transactions loadTransactions() {
		File transactionsFile = getTransactionsFile();
		if (transactionsFile.exists()) {
			return read(transactionsFile, Transactions.class);
		}
		return new Transactions();
	}

	public void saveSettings(Settings settings) {
		checkNotNull(settings);
		save(getSettingsFile(), settings);
	}

	public void saveTransactions(Transactions transactions) {
		checkNotNull(transactions);
		save(getTransactionsFile(), transactions);
	}

	private <T> T read(File file, Class<T> type) {
		try (Reader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(file)),
				StandardCharsets.UTF_8)) {
			return checkNotNull(gson().fromJson(reader, type));
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	private <T> void save(File file, T data) {
		checkNotNull(file);
		checkNotNull(data);
		File dataFolder = file.getParentFile();
		if (!dataFolder.exists()) {
			if (!dataFolder.mkdirs()) {
				throw new IllegalStateException(format("Cannot create folder {0}", dataFolder));
			}
		}
		Gson gson = gson();
		try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)),
				StandardCharsets.UTF_8)) {
			gson.toJson(data, writer);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	private Gson gson() {
		return gsonProvider.get();
	}

	File getTransactionsFile() {
		return new File(dataDirectoryLocator.locate(), TRANSACTIONS_FILE);
	}

	File getSettingsFile() {
		return new File(dataDirectoryLocator.locate(), SETTINGS_FILE);
	}
}

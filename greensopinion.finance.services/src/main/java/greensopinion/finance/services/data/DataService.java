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

import com.google.gson.Gson;

import jersey.repackaged.com.google.common.base.Throwables;

class DataService {

	private static final String SETTINGS_FILE = "settings.json";
	private final DataGsonProvider gsonProvider;
	private final DataDirectoryLocator dataDirectoryLocator;

	@Inject
	DataService(DataGsonProvider gsonProvider, DataDirectoryLocator dataDirectoryLocator) {
		this.gsonProvider = checkNotNull(gsonProvider);
		this.dataDirectoryLocator = checkNotNull(dataDirectoryLocator);
	}

	public Data load() {
		File dataFile = getSettingsFile();
		if (dataFile.exists()) {
			try (Reader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(dataFile)),
					StandardCharsets.UTF_8)) {
				return checkNotNull(gson().fromJson(reader, Data.class));
			} catch (IOException e) {
				throw Throwables.propagate(e);
			}
		}
		return new Data();
	}

	public void save(Data data) {
		checkNotNull(data);
		File dataFile = getSettingsFile();
		File dataFolder = dataFile.getParentFile();
		if (!dataFolder.exists()) {
			if (!dataFolder.mkdirs()) {
				throw new IllegalStateException(format("Cannot create folder {0}", dataFolder));
			}
		}
		Gson gson = gson();
		try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(dataFile)),
				StandardCharsets.UTF_8)) {
			gson.toJson(data, writer);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	private Gson gson() {
		return gsonProvider.get();
	}

	File getSettingsFile() {
		return new File(dataDirectoryLocator.locate(), SETTINGS_FILE);
	}
}

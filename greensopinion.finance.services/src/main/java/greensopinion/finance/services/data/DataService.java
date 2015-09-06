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

public class DataService {

	private static final String DATA_FILE = "data.json";
	private Gson gson;

	@Inject
	public DataService(Gson gson) {
		this.gson = checkNotNull(gson);
	}

	public Data load() {
		File dataFile = getDataFile();
		if (dataFile.exists()) {
			try (Reader reader = new InputStreamReader(new BufferedInputStream(new FileInputStream(dataFile)),
					StandardCharsets.UTF_8)) {
				return checkNotNull(gson.fromJson(reader, Data.class));
			} catch (IOException e) {
				throw Throwables.propagate(e);
			}
		}
		return new Data();
	}

	public void save(Data data) {
		checkNotNull(data);
		File dataFile = getDataFile();
		File dataFolder = dataFile.getParentFile();
		if (!dataFolder.exists()) {
			if (!dataFolder.mkdirs()) {
				throw new IllegalStateException(format("Cannot create folder {0}", dataFolder));
			}
		}
		try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(dataFile)),
				StandardCharsets.UTF_8)) {
			gson.toJson(data, writer);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	File getDataFile() {
		return new File(getDataDirectory(), DATA_FILE);
	}

	File getDataDirectory() {
		return DataDirectory.locate();
	}
}

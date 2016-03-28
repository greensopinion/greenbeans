/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package greensopinion.finance.services.persistence;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import com.google.common.base.Throwables;
import com.google.gson.Gson;

public abstract class PersistenceService<T> {

	private final PersistenceGsonProvider gsonProvider;
	private final DataDirectoryLocator dataDirectoryLocator;
	private final Class<T> type;

	PersistenceService(PersistenceGsonProvider gsonProvider, DataDirectoryLocator dataDirectoryLocator, Class<T> type) {
		this.gsonProvider = checkNotNull(gsonProvider);
		this.dataDirectoryLocator = checkNotNull(dataDirectoryLocator);
		this.type = checkNotNull(type);
	}

	public T load() {
		File dataFile = getFile();
		if (dataFile.exists()) {
			return read(dataFile);
		}
		return defaultInstance();
	}

	public void save(T value) {
		checkNotNull(value);
		save(getFile(), value);
	}

	private T read(File file) {
		try (InputStream stream = new FileInputStream(file)) {
			return read(stream);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	protected T read(InputStream stream) throws IOException {
		try (Reader reader = new InputStreamReader(new BufferedInputStream(stream), StandardCharsets.UTF_8)) {
			return checkNotNull(gson().fromJson(reader, type));
		}
	}

	private void save(File file, T data) {
		checkNotNull(file);
		checkNotNull(data);
		ensureFolder(file);
		Gson gson = gson();
		try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file)),
				StandardCharsets.UTF_8)) {
			gson.toJson(data, writer);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

	private void ensureFolder(File file) {
		File dataFolder = file.getParentFile();
		if (!dataFolder.exists()) {
			if (!dataFolder.mkdirs()) {
				throw new IllegalStateException(format("Cannot create folder {0}", dataFolder));
			}
		}
	}

	private Gson gson() {
		return gsonProvider.get();
	}

	File getFile() {
		return new File(dataDirectoryLocator.locate(), getFilename());
	}

	abstract String getFilename();

	abstract T defaultInstance();
}

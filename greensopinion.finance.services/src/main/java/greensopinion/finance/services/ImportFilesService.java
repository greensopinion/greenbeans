package greensopinion.finance.services;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

import greensopinion.finance.services.data.ConfigurationService;
import greensopinion.finance.services.transaction.CsvTransactionReader;
import greensopinion.finance.services.transaction.Transaction;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class ImportFilesService {

	private final Window window;
	private final ConfigurationService configurationService;

	@Inject
	ImportFilesService(Window window, ConfigurationService configurationService) {
		this.window = checkNotNull(window);
		this.configurationService = checkNotNull(configurationService);
	}

	public List<String> selectedFiles() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(initialDirectory());
		fileChooser.setTitle("Select Files to Import");
		fileChooser.getExtensionFilters().setAll(new ExtensionFilter("CSV files (*.csv)", ImmutableList.of("*.csv")));
		List<File> files = fileChooser.showOpenMultipleDialog(window);
		if (files == null) {
			return ImmutableList.of();
		}
		return FluentIterable.from(files).transform(new Function<File, String>() {

			@Override
			public String apply(File input) {
				return checkNotNull(input).getPath();
			}
		}).toSortedList(Ordering.natural());
	}

	public void importFiles(List<String> files, boolean deleteAfterImport) {
		checkNotNull(files, "Must provide files");

		List<Transaction> transactions = new ArrayList<>();
		for (String path : files) {
			transactions.addAll(importFile(path, deleteAfterImport));
		}
		addTransactions(transactions);
		if (deleteAfterImport) {
			deleteFiles(files);
		}
	}

	private void addTransactions(List<Transaction> transactions) {
		configurationService.addTransactions(transactions);
	}

	private void deleteFiles(List<String> files) {
		for (String path : files) {
			File file = new File(path);
			overwrite(file);
			file.delete();
		}
	}

	private void overwrite(File file) {
		long length = file.length();
		if (length <= 0L) {
			length = 10240L;
		}
		try (FileOutputStream out = new FileOutputStream(file)) {
			for (long i = 0; i < length; ++i) {
				out.write(0);
			}
		} catch (IOException e) {
			throw new RuntimeException(format("Cannot delete file {0}: {1}", file, e.getMessage()), e);
		}
	}

	private List<Transaction> importFile(String path, boolean deleteAfterImport) {
		File file = new File(path);
		checkArgument(file.exists(), "File does not exist: %s", file);
		checkArgument(file.isFile(), "Not a file: %s", file);
		try (CsvTransactionReader reader = new CsvTransactionReader(
				new InputStreamReader(new BufferedInputStream(new FileInputStream(file)), StandardCharsets.UTF_8))) {
			return reader.transactions();
		} catch (Exception e) {
			throw new RuntimeException(format("Cannot read file {0}: {1}", path, e.getMessage()), e);
		}
	}

	private File initialDirectory() {
		File file = new File(System.getProperty("user.home"), "Downloads");
		if (file.exists() && file.isDirectory()) {
			return file;
		}
		return new File(System.getProperty("user.home"));
	}
}

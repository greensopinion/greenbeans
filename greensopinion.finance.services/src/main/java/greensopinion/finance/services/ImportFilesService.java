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
package greensopinion.finance.services;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;

import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;
import greensopinion.finance.services.transaction.OfxTransactionReader;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class ImportFilesService {

	private final Window window;
	private final TransactionsService transactionsService;

	@Inject
	ImportFilesService(Window window, TransactionsService transactionsService) {
		this.window = checkNotNull(window);
		this.transactionsService = checkNotNull(transactionsService);
	}

	public List<String> selectedFiles() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(initialDirectory());
		fileChooser.setTitle("Select Files to Import");
		// ofx Microsoft Money
		// qfx Intuit Quicken
		// qbo Intuit Quick Books
		// aso Simply Accounting
		fileChooser.getExtensionFilters().setAll(new ExtensionFilter("Transaction files (*.ofx, *.qfx, *.qbo, *.aso)",
				ImmutableList.of("*.ofx", "*.qfx", "*.qbo", "*.aso")));
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

		Transactions transactions = transactionsService.retrieve();

		for (String path : files) {
			List<Transaction> fileTransactions = importFile(path);
			transactions = addTransactions(transactions, fileTransactions);
		}
		transactionsService.update(transactions);
		if (deleteAfterImport) {
			deleteFiles(files);
		}
	}

	private Transactions addTransactions(Transactions transactions, List<Transaction> provided) {
		List<Transaction> newTransactions = new ArrayList<>(provided);

		Set<Transaction> existingTransactions = new HashSet<>(transactions.getTransactions());
		for (Transaction newTransaction : ImmutableList.copyOf(newTransactions)) {
			if (existingTransactions.contains(newTransaction)) {
				newTransactions.remove(newTransaction);
			}
		}
		newTransactions.addAll(transactions.getTransactions());
		Collections.sort(newTransactions);
		return new Transactions(newTransactions);
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

	List<Transaction> importFile(String path) {
		File file = new File(path);
		checkArgument(file.exists(), "File does not exist: %s", file);
		checkArgument(file.isFile(), "Not a file: %s", file);
		try (OfxTransactionReader reader = new OfxTransactionReader(
				new BufferedInputStream(new FileInputStream(file)))) {
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

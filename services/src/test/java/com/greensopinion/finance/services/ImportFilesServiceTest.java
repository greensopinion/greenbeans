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
package com.greensopinion.finance.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.greensopinion.finance.services.ImportFilesService;
import com.greensopinion.finance.services.domain.Transaction;
import com.greensopinion.finance.services.domain.Transactions;
import com.greensopinion.finance.services.domain.TransactionsService;
import com.greensopinion.finance.services.transaction.MockTransaction;

import javafx.stage.Window;

public class ImportFilesServiceTest {

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private final TransactionsService transactionsService = mock(TransactionsService.class);

	private final ImportFilesService service = new ImportFilesService(mock(Window.class), transactionsService);

	@Test
	public void importFiles() {
		assertImportFiles(true);
	}

	@Test
	public void importFilesWithoutDelete() {
		assertImportFiles(false);
	}

	@Test
	public void importAvoidsDuplicatesWhenImportingTwoFilesWithTheSameTransactions() {
		Transaction txn1 = MockTransaction.create("2015-06-12", "description", -1263, "1234123412341234");
		Transaction txn2 = MockTransaction.create("2015-06-13", "description 2", -1234, "1234123412341234");
		Transactions originalTransactions = new Transactions(ImmutableList.of(txn1));
		doReturn(originalTransactions).when(transactionsService).retrieve();

		ImportFilesService service = new ImportFilesService(mock(Window.class), transactionsService) {
			@Override
			List<Transaction> importFile(String path) {
				return ImmutableList.of(txn2);
			}
		};

		service.importFiles(ImmutableList.of("a", "b"), false);

		ArgumentCaptor<Transactions> transactionsCaptor = ArgumentCaptor.forClass(Transactions.class);
		verify(transactionsService).update(transactionsCaptor.capture());

		Transactions transactions = transactionsCaptor.getValue();
		assertTransactions(ImmutableList.of(txn1, txn2), transactions.getTransactions());
	}

	@Test
	public void importAvoidsDuplicatesWhenImportingIgnoringDescriptionCase() {
		Transaction txn1 = MockTransaction.create("2015-06-12", "description", -1263, "1234123412341234");
		Transaction txn2 = MockTransaction.create("2015-06-13", "description 2", -1234, "1234123412341234");
		Transaction txn3 = MockTransaction.create("2015-06-12", "DESCRiption", -1263, "1234123412341234");
		Transactions originalTransactions = new Transactions(ImmutableList.of(txn1, txn2));
		doReturn(originalTransactions).when(transactionsService).retrieve();

		ImportFilesService service = new ImportFilesService(mock(Window.class), transactionsService) {
			@Override
			List<Transaction> importFile(String path) {
				return ImmutableList.of(txn3);
			}
		};

		service.importFiles(ImmutableList.of("a"), false);

		ArgumentCaptor<Transactions> transactionsCaptor = ArgumentCaptor.forClass(Transactions.class);
		verify(transactionsService).update(transactionsCaptor.capture());

		Transactions transactions = transactionsCaptor.getValue();
		assertTransactions(ImmutableList.of(txn1, txn2), transactions.getTransactions());
	}

	private void assertImportFiles(boolean deleteFileAfterImport) {
		Transaction txn1 = MockTransaction.create("2015-06-12", "description", -1263, "1234123412341234");
		Transaction txn2 = MockTransaction.create("2015-06-11", "description2", -1500, "1234123412341234");
		Transactions originalTransactions = new Transactions(ImmutableList.of(txn2, txn1));
		doReturn(originalTransactions).when(transactionsService).retrieve();

		File file1 = new File(temporaryFolder.getRoot(), "test1.qfx");
		write(file1, TestResources.load(ImportFilesServiceTest.class, "test1.qfx"));
		assertTrue(file1.exists());

		service.importFiles(ImmutableList.of(file1.getPath()), deleteFileAfterImport);

		assertEquals(!deleteFileAfterImport, file1.exists());

		ArgumentCaptor<Transactions> transactionsCaptor = ArgumentCaptor.forClass(Transactions.class);
		verify(transactionsService).update(transactionsCaptor.capture());

		Transaction txn3 = MockTransaction.create("2015-06-15", "return", 10341, "1234123412341234");
		Transactions transactions = transactionsCaptor.getValue();
		assertTransactions(ImmutableList.of(txn2, txn1, txn3), transactions.getTransactions());
	}

	private void assertTransactions(List<Transaction> expected, List<Transaction> transactions) {
		assertEquals(expected.size(), transactions.size());
		for (int x = 0; x < expected.size(); ++x) {
			assertEquals(expected.get(x), transactions.get(x));
		}
	}

	private void write(File file, String text) {
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
			writer.write(text);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
}

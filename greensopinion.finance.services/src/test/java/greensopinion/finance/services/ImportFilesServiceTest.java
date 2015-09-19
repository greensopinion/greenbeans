package greensopinion.finance.services;

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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.data.Transactions;
import greensopinion.finance.services.data.TransactionsService;
import greensopinion.finance.services.transaction.MockTransaction;
import greensopinion.finance.services.transaction.Transaction;
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

	private void assertImportFiles(boolean deleteFileAfterImport) {
		Transaction txn1 = MockTransaction.create("2015-06-12", "description", -1263);
		Transaction txn2 = MockTransaction.create("2015-06-11", "description2", -1500);
		Transactions originalTransactions = new Transactions(ImmutableList.of(txn2, txn1));
		doReturn(originalTransactions).when(transactionsService).retrieve();

		File file1 = new File(temporaryFolder.getRoot(), "test1.csv");
		write(file1, "06/12/2015,description,12.63,,5356.53\n06/15/2015,return,,103.41,5356.53");
		assertTrue(file1.exists());

		service.importFiles(ImmutableList.of(file1.getPath()), deleteFileAfterImport);

		assertEquals(!deleteFileAfterImport, file1.exists());

		ArgumentCaptor<Transactions> transactionsCaptor = ArgumentCaptor.forClass(Transactions.class);
		verify(transactionsService).update(transactionsCaptor.capture());

		Transaction txn3 = MockTransaction.create("2015-06-15", "return", 10341);
		Transactions transactions = transactionsCaptor.getValue();
		assertEquals(ImmutableList.of(txn2, txn1, txn3), transactions.getTransactions());
	}

	private void write(File file, String text) {
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
			writer.write(text);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
}

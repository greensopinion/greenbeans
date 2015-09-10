package greensopinion.finance.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.ArgumentCaptor;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.data.ConfigurationService;
import greensopinion.finance.services.transaction.Transaction;
import javafx.stage.Window;
import jersey.repackaged.com.google.common.base.Throwables;

public class ImportFilesServiceTest {

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private final ConfigurationService configurationService = mock(ConfigurationService.class);

	private final ImportFilesService service = new ImportFilesService(mock(Window.class), configurationService);

	@Test
	public void importFiles() {
		assertImportFiles(true);
	}

	@Test
	public void importFilesWithoutDelete() {
		assertImportFiles(false);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void assertImportFiles(boolean deleteFileAfterImport) {
		File file1 = new File(temporaryFolder.getRoot(), "test1.csv");
		write(file1, "06/12/2015,description,12.63,,5356.53");
		assertTrue(file1.exists());

		service.importFiles(ImmutableList.of(file1.getPath()), deleteFileAfterImport);

		assertEquals(!deleteFileAfterImport, file1.exists());

		ArgumentCaptor<List> transactionsCaptor = ArgumentCaptor.forClass(List.class);
		verify(configurationService).addTransactions(transactionsCaptor.capture());

		List<Transaction> transactions = transactionsCaptor.getValue();
		assertEquals(ImmutableList.of(transaction("2015-06-12", "description", -1263)), transactions);
	}

	private Transaction transaction(String date, String description, long amount) {
		try {
			return new Transaction(dateFormat().parse(date), description, amount);
		} catch (ParseException e) {
			throw Throwables.propagate(e);
		}
	}

	private DateFormat dateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	private void write(File file, String text) {
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
			writer.write(text);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}
}

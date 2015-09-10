package greensopinion.finance.services.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableList;

public class CsvTransactionReaderTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void close() throws IOException {
		Reader reader = mock(Reader.class);
		CsvTransactionReader transactionReader = new CsvTransactionReader(reader);
		verifyNoMoreInteractions(reader);
		transactionReader.close();
		verify(reader).close();
	}

	@Test
	public void readEmpty() throws IOException {
		Reader reader = new StringReader("\n");
		try (CsvTransactionReader transactionReader = new CsvTransactionReader(reader)) {
			assertEquals(ImmutableList.of(), transactionReader.transactions());
		}
	}

	@Test
	public void readInvalid() throws IOException {
		Reader reader = new StringReader("123423\n");
		try (CsvTransactionReader transactionReader = new CsvTransactionReader(reader)) {
			thrown.expect(InvalidTransactionException.class);
			thrown.expectMessage("Expected 5 CSV entries in record 1");
			transactionReader.transactions();
		}
	}

	@Test
	public void read() throws IOException {
		Reader reader = new StringReader(
				"06/12/2015,description,12.63,,5356.53\n06/13/2015,description,,15.00,5356.53");
		try (CsvTransactionReader transactionReader = new CsvTransactionReader(reader)) {
			List<Transaction> transactions = transactionReader.transactions();
			assertEquals(2, transactions.size());
			assertTransaction("2015-06-12", -1263, transactions.get(0));
			assertTransaction("2015-06-13", 1500, transactions.get(1));
		}
	}

	private void assertTransaction(String expectedDate, long amount, Transaction transaction) {
		assertNotNull(transaction);
		assertEquals(expectedDate, dateFormat().format(transaction.getDate()));
		assertEquals(amount, transaction.getAmount());
	}

	private DateFormat dateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}
}

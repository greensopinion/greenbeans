package greensopinion.finance.services.transaction;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.text.MessageFormat.format;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.domain.Transaction;

public class CsvTransactionReader implements Closeable {

	private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	private final Reader reader;

	public CsvTransactionReader(Reader reader) {
		this.reader = checkNotNull(reader);
	}

	public List<Transaction> transactions() {
		ImmutableList.Builder<Transaction> builder = ImmutableList.builder();
		try (CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
			for (CSVRecord record : csvParser) {
				// 06/12/2015,description,12.63,,5356.53
				try {
					checkState(record.size() == 5, "Expected 5 CSV entries");
					String date = record.get(0);
					String description = record.get(1);
					String debitAmount = record.get(2);
					String creditAmount = record.get(3);
					builder.add(new Transaction(parseDate(date), description, amount(debitAmount, creditAmount), null));
				} catch (Exception e) {
					throw new InvalidTransactionException(
							format("{0} in record {1}", e.getMessage(), record.getRecordNumber()), e);
				}
			}
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
		return builder.build();
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

	private Date parseDate(String date) {
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw Throwables.propagate(e);
		}
	}

	private long amount(String debitAmount, String creditAmount) {
		if (!Strings.isNullOrEmpty(debitAmount)) {
			checkState(Strings.isNullOrEmpty(creditAmount), "Expected empty credit amount");
			return -((long) (Float.parseFloat(debitAmount) * 100f));
		}
		checkState(!Strings.isNullOrEmpty(creditAmount), "Expected non-empty credit amount");
		return (long) (Float.parseFloat(creditAmount) * 100f);
	}

}

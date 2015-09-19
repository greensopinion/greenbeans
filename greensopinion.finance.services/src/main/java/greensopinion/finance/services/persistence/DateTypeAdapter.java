package greensopinion.finance.services.persistence;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class DateTypeAdapter extends TypeAdapter<Date> {

	private static final String RFC_3339_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	@Override
	public void write(JsonWriter writer, Date date) throws IOException {
		if (date == null) {
			writer.nullValue();
		} else {
			writer.value(dateFormat().format(date));
		}
	}

	@Override
	public Date read(JsonReader reader) throws IOException {
		if (reader.peek().equals(JsonToken.NULL)) {
			reader.nextNull();
			return null;
		}
		String value = reader.nextString();
		try {
			return dateFormat().parse(value);
		} catch (ParseException e) {
			throw new IOException(format("Expected a date but got \"{0}\"", value));
		}
	}

	private DateFormat dateFormat() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(RFC_3339_PATTERN);
		TimeZone utc = TimeZone.getTimeZone("UTC");
		dateFormat.setTimeZone(utc);
		dateFormat.setCalendar(new GregorianCalendar(utc));
		return dateFormat;
	}
}

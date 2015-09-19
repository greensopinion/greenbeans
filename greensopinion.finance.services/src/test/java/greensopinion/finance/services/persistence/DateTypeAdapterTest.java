package greensopinion.finance.services.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import greensopinion.finance.services.persistence.DateTypeAdapter;

public class DateTypeAdapterTest {
	private final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateTypeAdapter()).create();

	@Test
	public void toJson() {
		assertEquals("\"2015-09-08T22:45:31.031Z\"", gson.toJson(new Date(1441752331031L)));
	}

	@Test
	public void toJsonNull() {
		assertEquals("null", gson.toJson(null, Date.class));
	}

	@Test
	public void fromJson() {
		assertEquals(new Date(1441752331031L), gson.fromJson("\"2015-09-08T22:45:31.031Z\"", Date.class));
	}

	@Test
	public void fromJsonNull() {
		assertEquals(null, gson.fromJson("null", Date.class));
	}
}

package greensopinion.finance.services.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;

public class DataGsonProviderTest {

	@Test
	public void get() {
		Gson gson = new DataGsonProvider().get();
		assertNotNull(gson);
		assertHasRfc3339DateFormat(gson);
	}

	private void assertHasRfc3339DateFormat(Gson gson) {
		assertEquals("\"2015-09-08T22:45:31.031Z\"", gson.toJson(new Date(1441752331031L)));
	}
}

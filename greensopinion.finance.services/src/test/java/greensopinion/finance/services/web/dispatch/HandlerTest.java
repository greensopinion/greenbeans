package greensopinion.finance.services.web.dispatch;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.PathParam;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.collect.ImmutableMap;

public class HandlerTest {
	public static class TestWebService {
		public Object thrower() {
			throw new RuntimeException("ouch");
		}

		public String echo(@PathParam("message") String s) {
			return s;
		}
	}

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void invokeWithException() throws Exception {
		final Logger logger = mock(Logger.class);
		Handler handler = new Handler(new TestWebService(), TestWebService.class.getDeclaredMethod("thrower")) {
			@Override
			Logger getLogger() {
				return logger;
			}
		};
		try {
			handler.invoke(ImmutableMap.of());
		} catch (Exception e) {
			verify(logger).log(Level.SEVERE, "exception: " + e.getMessage(), e);
			verifyNoMoreInteractions(logger);
			throw e;
		} finally {
			thrown.expect(RuntimeException.class);
			thrown.expectMessage("ouch");
		}
	}

	@Test
	public void invokeWithParameter() throws Exception {
		Handler handler = new Handler(new TestWebService(),
				TestWebService.class.getDeclaredMethod("echo", String.class));
		assertEquals("test it yeah!", handler.invoke(ImmutableMap.of("message", "test it yeah!")));
	}
}

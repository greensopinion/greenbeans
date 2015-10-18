package greensopinion.finance.services.web.dispatch;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Map;

import javax.ws.rs.PathParam;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import greensopinion.finance.services.web.GsonWebRenderer;

public class InvokerTest {

	public static class MockWebService {
		public void get(@PathParam("a") String a) {
		}
	}

	private final Invoker invoker = new Invoker(new GsonWebRenderer(new Gson()));

	@Test
	public void convertEmptyString() throws Exception {
		assertEquals(null, invoker.convert(String.class, ""));
	}

	@Test
	public void createParametersWithEmptyString() throws Exception {
		Handler handler = createHandler("get");
		Map<String, Object> parameters = invoker.createParameters(new WebRequest("GET", "/path", null),
				new MatchResult(true, ImmutableMap.of("a", "")), handler);
		assertEquals(null, parameters.get("a"));
	}

	private Handler createHandler(String methodName) {
		MockWebService webService = new MockWebService();
		for (Method method : MockWebService.class.getDeclaredMethods()) {
			if (method.getName().equals(methodName)) {
				return new Handler(webService, method);
			}
		}
		throw new IllegalArgumentException(methodName);
	}
}

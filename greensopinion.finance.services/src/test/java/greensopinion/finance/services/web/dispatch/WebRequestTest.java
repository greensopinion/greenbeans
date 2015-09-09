package greensopinion.finance.services.web.dispatch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WebRequestTest {

	@Test
	public void create() {
		WebRequest webRequest = new WebRequest("GET", "/yo", "123");
		assertEquals("GET", webRequest.getHttpMethod());
		assertEquals("/yo", webRequest.getPath());
		assertEquals("123", webRequest.getEntity());
		assertEquals("WebRequest{httpMethod=GET, path=/yo}", webRequest.toString());
	}
}

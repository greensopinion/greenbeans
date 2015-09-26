package greensopinion.finance.services.bridge;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import greensopinion.finance.services.web.dispatch.WebDispatch;
import greensopinion.finance.services.web.dispatch.WebRequest;

public class WebInvokerTest {

	private final WebDispatch dispatch = mock(WebDispatch.class);
	private final WebInvoker invoker = new WebInvoker(dispatch);

	@Test
	public void invoke() {
		String requestPath = "/one/two%20three?four=five+six&seven=";
		invoker.invoke("GET", requestPath, null);
		WebRequest request = verifyDispatcherRequest();
		assertEquals("GET", request.getHttpMethod());
		assertEquals(null, request.getEntity());
		assertEquals(requestPath, request.getPath());
	}

	private WebRequest verifyDispatcherRequest() {
		ArgumentCaptor<WebRequest> requestCaptor = ArgumentCaptor.forClass(WebRequest.class);
		verify(dispatch).dispatch(requestCaptor.capture());
		return requestCaptor.getValue();
	}
}

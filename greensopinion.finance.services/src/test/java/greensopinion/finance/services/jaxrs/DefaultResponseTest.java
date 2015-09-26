package greensopinion.finance.services.jaxrs;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import org.junit.Test;

public class DefaultResponseTest {

	@Test
	public void statusInfo() {
		StatusType statusInfo = new DefaultResponse(404, null).getStatusInfo();
		assertEquals(404, statusInfo.getStatusCode());
		assertEquals(Family.CLIENT_ERROR, statusInfo.getFamily());
		assertEquals("Not Found", statusInfo.getReasonPhrase());
	}
}

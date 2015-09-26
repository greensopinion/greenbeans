package greensopinion.finance.services;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidationExceptionTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void requiresMessage() {
		thrown.expect(NullPointerException.class);
		new ValidationException(null);
	}

	@Test
	public void getMessage() {
		assertEquals("a message", new ValidationException("a message"));
	}
}

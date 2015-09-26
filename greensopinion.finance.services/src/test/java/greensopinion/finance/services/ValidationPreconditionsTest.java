package greensopinion.finance.services;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValidationPreconditionsTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void validateNotNullRequiresMessage() {
		thrown.expect(NullPointerException.class);
		ValidationPreconditions.validateNotNull(new Object(), null);
	}

	@Test
	public void validateNotNullRequiresMessageSentenceUpperCaseFirstLetter() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Message must start with an upper-case letter");
		ValidationPreconditions.validateNotNull(new Object(), "lower case first letter.");
	}

	@Test
	public void validateNotNullRequiresMessageSentenceEndsWithPeriod() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Message must end with a period");
		ValidationPreconditions.validateNotNull(new Object(), "Must have period");
	}

	@Test
	public void validateNotNull() {
		ValidationPreconditions.validateNotNull(new Object(), "Must provide a value.");
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Must provide a value.");
		ValidationPreconditions.validateNotNull(null, "Must provide a value.");
	}

	@Test
	public void validateNotNullOrEmpty() {
		ValidationPreconditions.validateNotNullOrEmpty("Not empty", "Must provide a value.");
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Must provide a value.");
		ValidationPreconditions.validateNotNullOrEmpty("   ", "Must provide a value.");
	}

	@Test
	public void validateNotNullOrEmptyNull() {
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Must provide a value.");
		ValidationPreconditions.validateNotNullOrEmpty(null, "Must provide a value.");
	}

	@Test
	public void validate() {
		ValidationPreconditions.validate(true, "No harm here.");
		thrown.expect(ValidationException.class);
		thrown.expectMessage("Ouch.");
		ValidationPreconditions.validate(false, "Ouch.");

	}
}

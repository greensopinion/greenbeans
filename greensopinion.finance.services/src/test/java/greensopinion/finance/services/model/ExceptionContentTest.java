package greensopinion.finance.services.model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class ExceptionContentTest {

	@Test
	public void simple() {
		ExceptionContent exceptionContent = new ExceptionContent(new Exception("arg"));
		assertEquals("arg", exceptionContent.getMessage());
		assertEquals("Exception", exceptionContent.getErrorCode());
	}

	@Test
	public void nested() {
		ExceptionContent exceptionContent = new ExceptionContent(new RuntimeException(new IOException("nope")));
		assertEquals("nope", exceptionContent.getMessage());
		assertEquals("RuntimeException", exceptionContent.getErrorCode());
	}

	@Test
	public void nested2() {
		ExceptionContent exceptionContent = new ExceptionContent(new RuntimeException(new IOException()));
		assertEquals("Unexpected exception: RuntimeException: IOException", exceptionContent.getMessage());
		assertEquals("RuntimeException", exceptionContent.getErrorCode());
	}
}

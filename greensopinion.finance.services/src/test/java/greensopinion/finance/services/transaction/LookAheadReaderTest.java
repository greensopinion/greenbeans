/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.transaction;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LookAheadReaderTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void createNull() throws IOException {
		thrown.expect(NullPointerException.class);
		try (LookAheadReader reader = new LookAheadReader(null)) {
		}
	}

	@Test
	public void readLine() throws IOException {
		try (LookAheadReader reader = new LookAheadReader(new StringReader("one\rtwo"))) {
			assertEquals("one", reader.readLine());
			assertEquals("two", reader.readLine());
			assertEquals(null, reader.readLine());
			assertEquals(null, reader.readLine());
		}
	}

	@Test
	public void close() throws IOException {
		Reader delegate = mock(Reader.class);
		LookAheadReader reader = new LookAheadReader(delegate);
		verifyNoMoreInteractions(delegate);
		reader.close();
		verify(delegate).close();
		reader.close();
		verify(delegate).close();
		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void peekLine() throws IOException {
		try (LookAheadReader reader = new LookAheadReader(new StringReader("one\rtwo"))) {
			assertEquals("one", reader.peekLine());
			assertEquals("one", reader.peekLine());
			assertEquals("one", reader.readLine());
			assertEquals("two", reader.peekLine());
			assertEquals("two", reader.readLine());
			assertEquals(null, reader.readLine());
			assertEquals(null, reader.readLine());
		}
	}

	@Test
	public void readAfterPeek() throws IOException {
		char[] buffer = new char[10];
		try (LookAheadReader reader = new LookAheadReader(new StringReader("one\r\ntwo"))) {
			reader.peekLine();
			assertEquals(1, reader.read(buffer, 1, 1));
			assertEquals('o', buffer[1]);
			assertEquals(3, reader.read(buffer, 2, 8));
			assertEquals('n', buffer[2]);
			assertEquals('e', buffer[3]);
			assertEquals('\n', buffer[4]);
			assertEquals(3, reader.read(buffer, 0, 10));
			assertEquals('t', buffer[0]);
			assertEquals('w', buffer[1]);
			assertEquals('o', buffer[2]);
		}
	}

	@Test
	public void readAfterPeekWithoutNewline() throws IOException {
		char[] buffer = new char[10];
		try (LookAheadReader reader = new LookAheadReader(new StringReader("one\r\ntwo"))) {
			reader.peekLine();
			assertEquals(3, reader.read(buffer, 1, 3));
			assertEquals('o', buffer[1]);
			assertEquals('n', buffer[2]);
			assertEquals('e', buffer[3]);
			assertEquals(1, reader.read(buffer, 0, 10));
			assertEquals('\n', buffer[0]);
			assertEquals(3, reader.read(buffer, 0, 10));
			assertEquals('t', buffer[0]);
			assertEquals('w', buffer[1]);
			assertEquals('o', buffer[2]);
		}
	}
}

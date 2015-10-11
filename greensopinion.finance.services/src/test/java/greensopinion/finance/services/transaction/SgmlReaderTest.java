package greensopinion.finance.services.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.StringReader;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import greensopinion.finance.services.transaction.SgmlReader.Token;

public class SgmlReaderTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Test
	public void none() throws IOException {
		SgmlReader reader = newReader("");
		assertNull(reader.readToken());
		assertNull(reader.readToken());
	}

	@Test
	public void singleTag() throws IOException {
		SgmlReader reader = newReader("<SDF>");
		Token token = reader.readToken();
		assertToken(Token.openTag("SDF"), token);
		assertNull(reader.readToken());
	}

	@Test
	public void openCloseTag() throws IOException {
		SgmlReader reader = newReader("<SDF>\n</SDF>");
		assertToken(Token.openTag("SDF"), reader.readToken());
		assertToken(Token.closeTag("SDF"), reader.readToken());
		assertNull(reader.readToken());
	}

	@Test
	public void tagsAndData() throws IOException {
		SgmlReader reader = newReader("<SDF>\n<QRS>SOME DATA 1.22\n</SDF>");
		assertToken(Token.openTag("SDF"), reader.readToken());
		assertToken(Token.openTag("QRS"), reader.readToken());
		assertToken(Token.data("SOME DATA 1.22"), reader.readToken());
		assertToken(Token.closeTag("SDF"), reader.readToken());
		assertNull(reader.readToken());
	}

	@Test
	public void dataWithSlash() throws IOException {
		SgmlReader reader = newReader("ab/c");
		assertToken(Token.data("ab/c"), reader.readToken());
		assertNull(reader.readToken());
	}

	@Test
	public void malformedUnexpectedOpen() throws IOException {
		SgmlReader reader = newReader("<<");
		thrown.expect(InvalidFileFormatException.class);
		reader.readToken();
	}

	@Test
	public void malformedUnexpectedWhitespaceInOpenTag() throws IOException {
		SgmlReader reader = newReader("< A");
		thrown.expect(InvalidFileFormatException.class);
		reader.readToken();
	}

	@Test
	public void malformedUnexpectedWhitespaceInOpenTag2() throws IOException {
		SgmlReader reader = newReader("<A B>");
		thrown.expect(InvalidFileFormatException.class);
		reader.readToken();
	}

	@Test
	public void malformedUnexpectedWhitespaceInOpenTag3() throws IOException {
		SgmlReader reader = newReader("<A\nB>");
		thrown.expect(InvalidFileFormatException.class);
		reader.readToken();
	}

	@Test
	public void malformedUnexpectedEndOfData() throws IOException {
		SgmlReader reader = newReader("<A");
		thrown.expect(InvalidFileFormatException.class);
		reader.readToken();
	}

	@Test
	public void malformedUnexpectedWhitespaceInCloseTag() throws IOException {
		SgmlReader reader = newReader("</ A");
		thrown.expect(InvalidFileFormatException.class);
		reader.readToken();
	}

	@Test
	public void malformedEmptyOpenTag() throws IOException {
		SgmlReader reader = newReader("<>");
		thrown.expect(InvalidFileFormatException.class);
		reader.readToken();
	}

	@Test
	public void malformedOpenTagWithSlash() throws IOException {
		SgmlReader reader = newReader("<AB/C>");
		thrown.expect(InvalidFileFormatException.class);
		reader.readToken();
	}

	@Test
	public void malformedEmptyCloseTag() throws IOException {
		SgmlReader reader = newReader("</>");
		thrown.expect(InvalidFileFormatException.class);
		reader.readToken();
	}

	@Test
	public void malformedDataWithGt() throws IOException {
		SgmlReader reader = newReader("A>");
		thrown.expect(InvalidFileFormatException.class);
		reader.readToken();
	}

	@Test
	public void entities() throws IOException {
		SgmlReader reader = newReader("<SDF>\n<QRS>One &amp; Two &#195;</SDF>");
		assertToken(Token.openTag("SDF"), reader.readToken());
		assertToken(Token.openTag("QRS"), reader.readToken());
		assertToken(Token.data("One & Two Ã"), reader.readToken());
		assertToken(Token.closeTag("SDF"), reader.readToken());
		assertNull(reader.readToken());
	}

	private void assertToken(Token expected, Token token) {
		assertNotNull(token);
		assertEquals(expected.getType(), token.getType());
		assertEquals(expected.getValue(), token.getValue());
	}

	private SgmlReader newReader(String string) {
		return new SgmlReader(new StringReader(string));
	}
}

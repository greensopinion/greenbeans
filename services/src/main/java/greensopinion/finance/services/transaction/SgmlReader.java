/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package greensopinion.finance.services.transaction;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.text.MessageFormat.format;

import java.io.Closeable;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

public class SgmlReader implements Closeable {
	enum TokenType {
		OPEN_TAG, CLOSE_TAG, DATA
	}

	public static class Token {
		public static Token openTag(String name) {
			return new Token(TokenType.OPEN_TAG, name);
		}

		public static Token closeTag(String name) {
			return new Token(TokenType.CLOSE_TAG, name);
		}

		public static Token data(String value) {
			return new Token(TokenType.DATA, value);
		}

		private final TokenType type;
		private final String value;

		public Token(TokenType type, String value) {
			this.type = checkNotNull(type);
			this.value = checkNotNull(value);
		}

		public String getValue() {
			return value;
		}

		public TokenType getType() {
			return type;
		}
	}

	private final PushbackReader reader;
	private final StringBuilder buffer = new StringBuilder();

	public SgmlReader(Reader reader) {
		this.reader = new PushbackReader(checkNotNull(reader), 1);
	}

	public Token readToken() throws IOException {
		buffer.delete(0, buffer.length());
		TokenType tokenType = null;
		int c;
		while ((c = reader.read()) != -1) {
			switch (c) {
			case '<':
				if (tokenType == null) {
					tokenType = TokenType.OPEN_TAG;
				} else if (tokenType == TokenType.DATA) {
					reader.unread(c);
					return new Token(tokenType, buffer.toString().trim());
				} else {
					throw new InvalidFileFormatException("Unexpected character '<'");
				}
				break;
			case '/':
				if (tokenType == TokenType.OPEN_TAG && buffer.toString().trim().length() == 0) {
					tokenType = TokenType.CLOSE_TAG;
				} else if (tokenType == TokenType.DATA) {
					buffer.append((char) c);
				} else {
					throw new InvalidFileFormatException("Unexpected character '/'");
				}
				break;
			case '>':
				if (tokenType == TokenType.OPEN_TAG || tokenType == TokenType.CLOSE_TAG) {
					String tagName = buffer.toString();
					if (tagName.length() == 0) {
						throw new InvalidFileFormatException(format("Invalid tag \"{0}\"", tagName));
					}
					return new Token(tokenType, tagName);
				} else {
					throw new InvalidFileFormatException("Unexpected character '>'");
				}
			case '&':
				String entity = "";
				while ((c = reader.read()) != -1 && c != ';') {
					entity += (char) c;
				}
				if (tokenType == null) {
					tokenType = TokenType.DATA;
				}
				buffer.append(EntityReferences.instance().namedEntityToString(entity));
				break;
			default:
				boolean isWhitespace = Character.isWhitespace((char) c);
				if (tokenType == null) {
					if (!isWhitespace) {
						tokenType = TokenType.DATA;
						buffer.append((char) c);
					}
				} else if ((tokenType == TokenType.OPEN_TAG || tokenType == TokenType.CLOSE_TAG) && isWhitespace) {
					throw new InvalidFileFormatException("Unexpected whitespace");
				} else {
					buffer.append((char) c);
				}
			}
		}
		if (tokenType == TokenType.DATA) {
			return new Token(tokenType, buffer.toString().trim());
		} else if (tokenType != null) {
			throw new InvalidFileFormatException("Unexpected end of data");
		}
		return null;
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}
}

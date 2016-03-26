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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

class LookAheadReader extends Reader {
	private final BufferedReader reader;

	private String nextLine;

	private int nextLineIndex;

	LookAheadReader(Reader reader) {
		this.reader = new BufferedReader(checkNotNull(reader));
	}

	public String readLine() throws IOException {
		fillNextLine();
		String line = nextLine;
		nextLine = null;
		return line;
	}

	public String peekLine() throws IOException {
		fillNextLine();
		return nextLine;
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

	private void fillNextLine() throws IOException {
		if (nextLine == null) {
			nextLine = reader.readLine();
			nextLineIndex = 0;
		}
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		if (nextLine != null) {
			int available = nextLine.length() - nextLineIndex;
			if (available == 0) {
				// unread newline
				nextLine = null;
				cbuf[off] = '\n';
				return 1;
			} else if (available > 0) {
				int toRead = Math.min(len, available);
				int offset = off;
				for (int x = 0; x < toRead; ++x, ++offset) {
					cbuf[offset] = nextLine.charAt(nextLineIndex++);
				}
				if (toRead < len) {
					nextLine = null;
					cbuf[offset++] = '\n';
				}
				return offset - off;
			}
		}
		nextLine = null;
		return reader.read(cbuf, off, len);
	}
}

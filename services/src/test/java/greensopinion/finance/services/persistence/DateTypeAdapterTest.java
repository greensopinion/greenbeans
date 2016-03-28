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
package greensopinion.finance.services.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import greensopinion.finance.services.persistence.DateTypeAdapter;

public class DateTypeAdapterTest {
	private final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateTypeAdapter()).create();

	@Test
	public void toJson() {
		assertEquals("\"2015-09-08T22:45:31.031Z\"", gson.toJson(new Date(1441752331031L)));
	}

	@Test
	public void toJsonNull() {
		assertEquals("null", gson.toJson(null, Date.class));
	}

	@Test
	public void fromJson() {
		assertEquals(new Date(1441752331031L), gson.fromJson("\"2015-09-08T22:45:31.031Z\"", Date.class));
	}

	@Test
	public void fromJsonNull() {
		assertEquals(null, gson.fromJson("null", Date.class));
	}
}

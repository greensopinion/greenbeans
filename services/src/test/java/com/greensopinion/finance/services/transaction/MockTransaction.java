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
package com.greensopinion.finance.services.transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.common.base.Throwables;
import com.greensopinion.finance.services.domain.Transaction;

public class MockTransaction {
	/**
	 * @param date
	 *            the date in "yyyy-MM-dd" format
	 * @param description
	 *            the description
	 * @param amount
	 *            the amount
	 * @return
	 */
	public static Transaction create(String date, String description, long amount) {
		return create(date, description, amount, null);
	}

	public static Transaction create(String date, String description, long amount, String accountNumber) {
		try {
			return new Transaction(dateFormat().parse(date), description, amount, null, accountNumber);
		} catch (ParseException e) {
			throw Throwables.propagate(e);
		}
	}

	private static DateFormat dateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}

	private MockTransaction() {
		// prevent instantiation
	}
}

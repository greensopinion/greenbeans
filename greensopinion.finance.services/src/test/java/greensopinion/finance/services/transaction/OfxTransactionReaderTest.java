/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Charsets;

import greensopinion.finance.services.TestResources;
import greensopinion.finance.services.domain.Transaction;

public class OfxTransactionReaderTest {

	@Test
	public void readCreditCard() throws IOException {
		List<Transaction> transactions = readTransactions("qfx-credit-card.sgml");
		assertEquals(3, transactions.size());
		assertTransaction("2013-09-14", -2378, "BUY LOW FOODS #086117    WHITE R", "1234123412341234",
				transactions.get(0));
		assertTransaction("2013-09-14", -2310, "KDK CLOTHING             WHITE R", "1234123412341234",
				transactions.get(1));
		assertTransaction("2013-09-18", 5660, "THE CHILDREN'S PLACE#3257SURREY", "1234123412341234",
				transactions.get(2));
	}

	@Test
	public void readCreditCardChase() throws IOException {
		List<Transaction> transactions = readTransactions("ofx-credit-card-chase.sgml");
		assertEquals(2, transactions.size());
		assertTransaction("2016-01-17", -1979, "PAPA JOHN'S #02714", "1234123412341234", transactions.get(0));
		assertTransaction("2016-01-04", 3284, "TARGET        00009951", "1234123412341234", transactions.get(1));
	}

	@Test
	public void readNextNonBlankLine() throws IOException {
		try (OfxTransactionReader reader = new OfxTransactionReader(
				new ByteArrayInputStream("\n\r\none\n\rtwo".getBytes(StandardCharsets.UTF_8)))) {
			assertEquals("one", reader.readNextNonBlankLine());
			assertEquals("two", reader.readNextNonBlankLine());
			assertEquals(null, reader.readNextNonBlankLine());
		}
	}

	@Test
	public void readBankAccount() throws IOException {
		List<Transaction> transactions = readTransactions("qfx-bank-account.sgml");
		assertEquals(3, transactions.size());
		assertTransaction("2013-09-30", 52544, "ACME INC PAY", "1234567891122334", transactions.get(0));
		assertTransaction("2013-09-30", -125355, "DW               MTG", "1234567891122334", transactions.get(1));
		assertTransaction("2013-09-30", -1495, "MONTHLY ACCOUNT FEE", "1234567891122334", transactions.get(2));
	}

	@Test
	public void readCreditUnionFormat() throws IOException {
		List<Transaction> transactions = readTransactions("qfx-creditunion-format.sgml");
		assertEquals(3, transactions.size());
		assertTransaction("2015-10-19", -835, "DAILY GRIND CAFE       VANCO", "1000033330PC00000DC011101C",
				transactions.get(0));
		assertTransaction("2015-10-19", -40000, "Online Transfer Out", "1000033330PC00000DC011101C",
				transactions.get(1));
		assertTransaction("2015-10-18", -3762, "CEDAR COTTAGE LIQUOR S VANCO", "1000033330PC00000DC011101C",
				transactions.get(2));
	}

	private List<Transaction> readTransactions(String resourceName) throws IOException {
		String inputData = TestResources.load(OfxTransactionReaderTest.class, resourceName);
		List<Transaction> transactions;
		try (OfxTransactionReader reader = new OfxTransactionReader(
				new ByteArrayInputStream(inputData.getBytes(Charsets.US_ASCII)))) {
			transactions = reader.transactions();
		}
		return transactions;
	}

	private void assertTransaction(String expectedDate, long amount, String desc, String accountNumber,
			Transaction transaction) {
		assertNotNull(transaction);
		assertEquals(expectedDate, dateFormat().format(transaction.getDate()));
		assertEquals(amount, transaction.getAmount());
		assertEquals(desc, transaction.getDescription());
		assertEquals(accountNumber, transaction.getAccountNumber());
	}

	private DateFormat dateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}
}

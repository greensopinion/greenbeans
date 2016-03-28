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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;

import greensopinion.finance.services.TestResources;
import greensopinion.finance.services.domain.Category;
import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.domain.Transactions;

public class TransactionsPersistenceServiceTest {

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File dataFolder;

	private DataDirectoryLocator dataDirectory;

	private TransactionsPersistenceService service;

	@Before
	public void before() {
		dataFolder = new File(temporaryFolder.getRoot(), "data");
		dataDirectory = new DataDirectoryLocator() {
			@Override
			public File locate() {
				return dataFolder;
			}
		};
		service = new TransactionsPersistenceService(MockPersistenceGsonProvider.create(), dataDirectory);
	}

	@Test
	public void defaultValue() {
		Transactions transactions = service.load();
		assertNotNull(transactions);
		assertEquals(ImmutableList.of(), transactions.getTransactions());
	}

	@Test
	public void transactionsRoundTrip() throws IOException {
		Transactions transactions = new Transactions(ImmutableList
				.of(new Transaction(new Date(1443322433000L), "a desc", 12345, new Category("A category"), null)
						.withId("1234")));
		service.save(transactions);

		String value = Files.toString(service.getFile(), StandardCharsets.UTF_8);
		assertEquals(TestResources.load(TransactionsPersistenceServiceTest.class, "expected-transactions.json.txt"),
				value);

		Transactions loaded = service.load();
		assertNotNull(loaded);
		assertEquals(transactions.getTransactions(), loaded.getTransactions());
		assertCategoriesNotStored(loaded);
	}

	private void assertCategoriesNotStored(Transactions loaded) {
		for (Transaction transaction : loaded.getTransactions()) {
			assertNull(transaction.getCategory());
		}
	}
}

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
package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.AbstractIntegrationTest;

public class DomainIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void transactionServiceIsEmpty() {
		TransactionsService transactionsService = injector.getInstance(TransactionsService.class);
		Transactions transactions = transactionsService.retrieve();
		assertNotNull(transactions);
		assertEquals(ImmutableList.of(), transactions.getTransactions());
	}
}
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import greensopinion.finance.services.domain.Transaction;

public class TransactionNormalizerTest {
	private final TransactionNormalizer normalizer = new TransactionNormalizer();

	@Test
	public void normalizeNone() {
		assertEquals(ImmutableList.of(), normalizer.normalize(ImmutableList.of()));
	}

	@Test
	public void normalizeTwoEqualPositives() {
		Transaction transaction1 = mockTransaction(10234);
		Transaction transaction2 = mockTransaction(10234);
		assertEquals(ImmutableList.of(transaction1, transaction2),
				normalizer.normalize(ImmutableList.of(transaction1, transaction2)));
	}

	@Test
	public void normalizeTwoEqualNegatives() {
		Transaction transaction1 = mockTransaction(-10234);
		Transaction transaction2 = mockTransaction(-10234);
		assertEquals(ImmutableList.of(transaction1, transaction2),
				normalizer.normalize(ImmutableList.of(transaction1, transaction2)));
	}

	@Test
	public void normalizeSome() {
		Transaction transaction0 = mockTransaction(452);
		Transaction transaction1 = mockTransaction(-10234);
		Transaction transaction2 = mockTransaction(10234);
		Transaction transaction3 = mockTransaction(453);
		assertEquals(ImmutableList.of(transaction0, transaction3),
				normalizer.normalize(ImmutableList.of(transaction0, transaction1, transaction2, transaction3)));
	}

	@Test
	public void normalizeSomeDoesntDoubleCancel() {
		Transaction transaction1 = mockTransaction(-10234);
		Transaction transaction2 = mockTransaction(10234);
		Transaction transaction3 = mockTransaction(10234);
		assertEquals(ImmutableList.of(transaction3),
				normalizer.normalize(ImmutableList.of(transaction1, transaction2, transaction3)));
	}

	private Transaction mockTransaction(long amount) {
		return MockTransaction.create("2015-01-03", "desc", amount);
	}

}

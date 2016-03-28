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
package com.greensopinion.finance.services.encryption;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import com.greensopinion.finance.services.domain.Categories;
import com.greensopinion.finance.services.domain.CategoriesService;
import com.greensopinion.finance.services.domain.Transactions;
import com.greensopinion.finance.services.domain.TransactionsService;
import com.greensopinion.finance.services.encryption.MasterPasswordChangeSupport;

public class MasterPasswordChangeSupportTest {

	private final TransactionsService transactionsService = mock(TransactionsService.class);
	private final CategoriesService categoriesService = mock(CategoriesService.class);
	private final Logger logger = mock(Logger.class);
	private final MasterPasswordChangeSupport support = new MasterPasswordChangeSupport(transactionsService,
			categoriesService, logger);
	private final Transactions transactions = mock(Transactions.class);
	private final Categories categories = mock(Categories.class);

	@Before
	public void before() {
		doReturn(transactions).when(transactionsService).retrieve();
		doReturn(categories).when(categoriesService).retrieve();
	}

	@Test
	public void support() {
		verifyZeroInteractions(transactionsService, categoriesService);
		support.aboutToChangeEncryptor();

		verify(transactionsService).retrieve();
		verify(categoriesService).retrieve();
		verifyNoMoreInteractions(transactionsService, categoriesService);

		support.encryptorChanged();

		InOrder inOrder = inOrder(transactionsService, categoriesService, logger);
		inOrder.verify(transactionsService).update(transactions);
		inOrder.verify(categoriesService).update(categories);
		inOrder.verify(logger).info("encrypted data with new master password");
		inOrder.verifyNoMoreInteractions();
	}
}

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
package com.greensopinion.finance.services.application;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.greensopinion.finance.services.application.Constants;

import javafx.application.Application.Parameters;

public class ConstantsTest {

	private final Parameters parameters = mock(Parameters.class);
	private List<String> unnamed;

	@Before
	public void before() {
		unnamed = new ArrayList<>();
		doReturn(unnamed).when(parameters).getUnnamed();
	}

	@Test
	public void webViewLocation() {
		File base = new File(".").getAbsoluteFile().getParentFile().getParentFile();
		assertEquals("file://" + base.toString() + "/ui/dist/index.html", Constants.webViewLocation(parameters));
	}

	@Test
	public void webViewLocationWithParameter() {
		unnamed.add("-XexternalUI");
		assertEquals("http://localhost:9000", Constants.webViewLocation(parameters));
	}
}

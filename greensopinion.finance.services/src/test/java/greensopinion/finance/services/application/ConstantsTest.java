/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.application;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
		assertEquals("file://" + base.toString() + "/greensopinion.finance.ui/dist/index.html",
				Constants.webViewLocation(parameters));
	}

	@Test
	public void webViewLocationWithParameter() {
		unnamed.add("-XexternalUI");
		assertEquals("http://localhost:9000", Constants.webViewLocation(parameters));
	}
}

/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class Eula {

	private final String text;

	public Eula(String text) {
		this.text = checkNotNull(text);
	}

	public String getText() {
		return text;
	}
}

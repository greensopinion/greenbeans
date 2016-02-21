/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.model;

public class CategoryRuleModel {
	private final String matchDescription;

	public CategoryRuleModel(String matchDescription) {
		this.matchDescription = matchDescription;
	}

	public String getMatchDescription() {
		return matchDescription;
	}
}

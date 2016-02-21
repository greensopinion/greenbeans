/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.web.dispatch;

public class WebResponse {

	private int responseCode;
	private String entity;

	public WebResponse(int responseCode, String entity) {
		this.responseCode = responseCode;
		this.entity = entity;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getEntity() {
		return entity;
	}
}

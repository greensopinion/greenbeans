/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.bridge;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.logging.Logger;

import javax.inject.Inject;

public class ConsoleBridge {

	private final Logger logger;

	@Inject
	public ConsoleBridge(Logger logger) {
		this.logger = checkNotNull(logger);
	}

	public void log(String text) {
		logger.info(text);
	}

	public void info(String text) {
		logger.info(text);
	}

	public void warn(String text) {
		logger.warning(text);
	}

	public void error(String text) {
		logger.severe(text);
	}
}

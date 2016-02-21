/*******************************************************************************
 * Copyright (c) 2015 David Green.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the EULA
 * which accompanies this distribution.
 *******************************************************************************/
package greensopinion.finance.services.logging;

import java.util.logging.Logger;

class AppLogging {
	public static Logger logger() {
		return Logger.getLogger(loggerName());
	}

	private static String loggerName() {
		String className = AppLogging.class.getName();
		return className.substring(0, className.indexOf('.'));
	}

	private AppLogging() {
		// prevent instantiation
	}
}

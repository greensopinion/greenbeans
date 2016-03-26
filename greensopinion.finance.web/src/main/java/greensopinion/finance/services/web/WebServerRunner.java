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
package greensopinion.finance.services.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

import com.google.common.util.concurrent.UncaughtExceptionHandlers;

import greensopinion.finance.services.demo.Demo;

public class WebServerRunner {

	private static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Thread.currentThread().setUncaughtExceptionHandler(UncaughtExceptionHandlers.systemExit());

		maybeEnableDemo();

		Server server = new Server(PORT);

		ServletContextHandler contextHandler = new ServletContextHandler(server, "/");
		addDefaultServlet(contextHandler);

		addJerseyServlet(contextHandler);
		addCorHeadersFilter(contextHandler);

		server.start();
		server.join();
	}

	private static void maybeEnableDemo() {
		if (Demo.isEnabled()) {
			new Demo().setup();
		}
	}

	private static void addDefaultServlet(ServletContextHandler contextHandler) {
		ServletHolder servletHolder = contextHandler.addServlet(DefaultServlet.class, "/");
		servletHolder.setInitParameter("resourceBase", "../greensopinion.finance.ui/dist/");
	}

	private static void addJerseyServlet(ServletContextHandler contextHandler) {
		ServletHolder jerseyServletHolder = new ServletHolder(new ServletContainer());
		jerseyServletHolder.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS,
				Application.class.getCanonicalName());
		contextHandler.addServlet(jerseyServletHolder, "/api/*");
	}

	private static void addCorHeadersFilter(ServletContextHandler contextHandler) {
		FilterHolder filterHolder = new FilterHolder(new CorHeaderFilter());
		contextHandler.addFilter(filterHolder, "/api/*", EnumSet.of(DispatcherType.REQUEST));
	}

}

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

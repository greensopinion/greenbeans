package greensopinion.finance.services.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

import com.google.common.util.concurrent.UncaughtExceptionHandlers;

public class WebServerRunner {

	private static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Thread.currentThread().setUncaughtExceptionHandler(UncaughtExceptionHandlers.systemExit());

		Server server = new Server(PORT);

		ServletContextHandler contextHandler = new ServletContextHandler(server, "/");

		addJerseyServlet(contextHandler);
		addCorHeadersFilter(contextHandler);

		server.start();
		server.join();
	}

	protected static void addJerseyServlet(ServletContextHandler contextHandler) {
		ServletHolder jerseyServletHolder = new ServletHolder(new ServletContainer());
		jerseyServletHolder.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS,
				Application.class.getCanonicalName());
		contextHandler.addServlet(jerseyServletHolder, "/*");
	}

	private static void addCorHeadersFilter(ServletContextHandler contextHandler) {
		FilterHolder filterHolder = new FilterHolder(new CorHeaderFilter());
		contextHandler.addFilter(filterHolder, "/*", EnumSet.of(DispatcherType.REQUEST));
	}

}

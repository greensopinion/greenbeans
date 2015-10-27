package greensopinion.finance.services.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CorHeaderFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// nothing to do
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		addCorHeaders((HttpServletResponse) response);

		chain.doFilter(request, response);
	}

	private void addCorHeaders(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	}

	@Override
	public void destroy() {
		// nothing to do
	}
}

package greensopinion.finance.services.web.dispatch;

import static java.text.MessageFormat.format;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(WebRequest request) {
		super(format("Not found: {0} {1}", request.getHttpMethod(), request.getPath()));
	}
}

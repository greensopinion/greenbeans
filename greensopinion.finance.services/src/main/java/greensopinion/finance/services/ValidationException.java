package greensopinion.finance.services;

import static com.google.common.base.Preconditions.checkNotNull;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ValidationException(String message) {
		super(checkNotNull(message));
	}
}

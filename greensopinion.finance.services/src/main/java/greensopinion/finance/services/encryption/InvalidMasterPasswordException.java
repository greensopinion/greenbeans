package greensopinion.finance.services.encryption;

import greensopinion.finance.services.ValidationException;

public class InvalidMasterPasswordException extends ValidationException {
	private static final long serialVersionUID = 1L;

	public InvalidMasterPasswordException() {
		super("Invalid master password.  Please try again.");
	}
}

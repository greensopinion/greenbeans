package greensopinion.finance.services.encryption;

public class InvalidMasterPasswordException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public InvalidMasterPasswordException() {
		super("Invalid master password.  Please try again.");
	}
}

package greensopinion.finance.services.transaction;

public class InvalidTransactionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidTransactionException(String message, Throwable cause) {
		super(message, cause);
	}

}

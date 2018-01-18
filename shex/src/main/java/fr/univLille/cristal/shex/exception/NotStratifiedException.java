package fr.univLille.cristal.shex.exception;

public class NotStratifiedException extends Exception {

	public NotStratifiedException() {
	}

	public NotStratifiedException(String message) {
		super(message);
	}

	public NotStratifiedException(Throwable cause) {
		super(cause);
	}

	public NotStratifiedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotStratifiedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

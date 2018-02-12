package fr.univLille.cristal.shex.exception;

public class CyclicReferencesException extends Exception {

	public CyclicReferencesException() {
		super();
	}

	public CyclicReferencesException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CyclicReferencesException(String message, Throwable cause) {
		super(message, cause);
	}

	public CyclicReferencesException(String message) {
		super(message);
	}

	public CyclicReferencesException(Throwable cause) {
		super(cause);
	}

}

package fr.univLille.cristal.shex.exception;

public class UndefinedReferenceException extends Exception {

	public UndefinedReferenceException() {
		super();
	}

	public UndefinedReferenceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UndefinedReferenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UndefinedReferenceException(String message) {
		super(message);
	}

	public UndefinedReferenceException(Throwable cause) {
		super(cause);
	}



}

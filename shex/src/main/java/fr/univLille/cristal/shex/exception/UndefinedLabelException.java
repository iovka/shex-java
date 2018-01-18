package fr.univLille.cristal.shex.exception;

public class UndefinedLabelException extends Exception {

	public UndefinedLabelException() {
		super();
	}

	public UndefinedLabelException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UndefinedLabelException(String message, Throwable cause) {
		super(message, cause);
	}

	public UndefinedLabelException(String message) {
		super(message);
	}

	public UndefinedLabelException(Throwable cause) {
		super(cause);
	}



}

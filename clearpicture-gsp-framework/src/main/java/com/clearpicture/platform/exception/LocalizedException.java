package com.clearpicture.platform.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LocalizedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6145684982164318709L;

	public LocalizedException(String message) {
		super(message);
	}

	public LocalizedException(Throwable cause) {
		super(cause);
	}

	public LocalizedException(String message, Throwable cause) {
		super(message, cause);
	}
}

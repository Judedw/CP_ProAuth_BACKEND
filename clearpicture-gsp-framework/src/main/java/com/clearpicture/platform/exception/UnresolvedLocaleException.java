package com.clearpicture.platform.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnresolvedLocaleException extends LocalizedException {
	private static final long serialVersionUID = 7436361288749125603L;

	public UnresolvedLocaleException(String message) {
		super(message);
	}

	public UnresolvedLocaleException(Throwable cause) {
		super(cause);
	}

	public UnresolvedLocaleException(String message, Throwable cause) {
		super(message, cause);
	}
}

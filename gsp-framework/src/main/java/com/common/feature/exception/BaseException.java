package com.common.feature.exception;

/**
 * BaseException
 * Created by nuwan on 7/21/18.
 */
public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 58551791813193151L;

    public BaseException() {
        super();
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}

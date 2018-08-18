package com.clearpicture.platform.product.exception;

/**
 * FileStorageException
 * Created by nuwan on 8/8/18.
 */
public class FileStorageException extends RuntimeException {

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

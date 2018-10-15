package com.clearpicture.platform.exception;

/**
 * @author Chamith
 */
public class SubscriptionValidationException extends BaseException {

    private static final long serialVersionUID = 3407865173447729724L;

    private String field;

    private String code;

    public SubscriptionValidationException(String field, String code) {
        this.field = field;
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }
}

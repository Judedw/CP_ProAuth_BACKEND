package com.clearpicture.platform.enums;

/**
 * RestApiResponseStatus
 * Created by nuwan on 7/12/18.
 */
public enum RestApiResponseStatus {

    OK("OK", 200), VALIDATION_FAILURE("CLIENT_ERROR", 400), ERROR("SYSTEM_ERROR", 500);

    private String status;

    private Integer code;

    RestApiResponseStatus(String status, Integer code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return status + ":" + code;
    }
}

package com.clearpicture.platform.response.wrapper;

import com.clearpicture.platform.enums.RestApiResponseStatus;

/**
 * SimpleResponseWrapper
 * Created by nuwan on 7/12/18.
 */
public class SimpleResponseWrapper<T> extends BaseResponseWrapper {

    private T content;

    public SimpleResponseWrapper(T content) {
        super(RestApiResponseStatus.OK);
        this.content = content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}

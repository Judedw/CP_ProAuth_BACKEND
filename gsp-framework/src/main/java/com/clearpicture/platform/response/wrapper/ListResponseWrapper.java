package com.clearpicture.platform.response.wrapper;

import com.clearpicture.platform.enums.RestApiResponseStatus;

import java.util.List;

/**
 * ListResponseWrapper
 * Created by nuwan on 7/12/18.
 */

public class ListResponseWrapper<T> extends BaseResponseWrapper {

    private List<T> content;

    public ListResponseWrapper(List<T> content) {
        super(RestApiResponseStatus.OK);
        this.content = content;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}

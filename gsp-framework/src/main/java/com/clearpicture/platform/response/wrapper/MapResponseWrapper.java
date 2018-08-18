package com.clearpicture.platform.response.wrapper;

import com.clearpicture.platform.enums.RestApiResponseStatus;

import java.util.List;
import java.util.Map;

/**
 * MapResponseWrapper
 * Created by nuwan on 7/12/18.
 */
public class MapResponseWrapper<T> extends BaseResponseWrapper {

    private Map<String, List<T>> content;

    public MapResponseWrapper(Map<String, List<T>> content) {
        super(RestApiResponseStatus.OK);
        this.content = content;
    }

    public Map<String, List<T>> getContent() {
        return content;
    }

    public void setContent(Map<String, List<T>> content) {
        this.content = content;
    }
}

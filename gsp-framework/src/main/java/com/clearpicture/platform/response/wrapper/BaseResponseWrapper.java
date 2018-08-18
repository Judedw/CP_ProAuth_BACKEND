package com.clearpicture.platform.response.wrapper;

import com.clearpicture.platform.enums.RestApiResponseStatus;
import lombok.Data;

/**
 * BaseResponseWrapper
 * Created by nuwan on 7/12/18.1
 */
@Data
public class BaseResponseWrapper {

    public static BaseResponseWrapper OK = new BaseResponseWrapper(RestApiResponseStatus.OK);

    public BaseResponseWrapper(RestApiResponseStatus restApiResponseStatus){
        this.status = restApiResponseStatus.getStatus();
        this.statusCode = restApiResponseStatus.getCode();
    }

    public String status;

    public Integer statusCode;

}

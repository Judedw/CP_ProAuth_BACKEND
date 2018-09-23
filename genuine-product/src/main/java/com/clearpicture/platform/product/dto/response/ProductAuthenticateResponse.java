package com.clearpicture.platform.product.dto.response;

import lombok.Data;

/**
 * ProductAuthenticateResponse
 * Created by nuwan on 8/2/18.
 */
@Data
public class ProductAuthenticateResponse {

    private ProductAuthData content;

    @Data
    public static  class ProductAuthData {

        private String surveyId;

        private String title;

        private String message;

    }
}

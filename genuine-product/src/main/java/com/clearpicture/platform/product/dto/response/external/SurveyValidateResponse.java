package com.clearpicture.platform.product.dto.response.external;

import lombok.Data;

/**
 * SurveyValidateResponse
 * Created by nuwan on 9/23/18.
 */
@Data
public class SurveyValidateResponse {

    private SurveyValidateData content;

    @Data
    public static  class SurveyValidateData {

        private Boolean isValid;

    }
}

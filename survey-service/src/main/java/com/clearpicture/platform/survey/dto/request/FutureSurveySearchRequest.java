package com.clearpicture.platform.survey.dto.request;


import com.clearpicture.platform.dto.request.BaseSearchRequest;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * FutureSurveySearchRequest
 * Created by Raveen on 11/06/18.
 */
@Data
public class FutureSurveySearchRequest extends BaseSearchRequest {

    private String sortProperty = "lastModifiedDate";



    @Pattern(regexp = "lastModifiedDate|name", flags = Pattern.Flag.CASE_INSENSITIVE, message = "{futureSurveySearchRequest.sortProperty.invalid}")
    @Override
    public String getSortProperty() {
        return sortProperty;
    }
}

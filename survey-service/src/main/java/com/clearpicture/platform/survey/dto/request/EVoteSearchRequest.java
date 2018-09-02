package com.clearpicture.platform.survey.dto.request;

import com.clearpicture.platform.dto.request.BaseSearchRequest;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * EVoteSearchRequest
 * Created by nuwan on 8/23/18.
 */
@Data
public class EVoteSearchRequest extends BaseSearchRequest {

    private String sortProperty = "lastModifiedDate";

    //@NotBlank(message = "ClientSearchRequest.search.name.empty")
    private String topic;

    @Pattern(regexp = "lastModifiedDate|name", flags = Pattern.Flag.CASE_INSENSITIVE, message = "{productSearchRequest.sortProperty.invalid}")
    @Override
    public String getSortProperty() {
        return sortProperty;
    }
}

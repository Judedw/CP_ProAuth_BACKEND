package com.clearpicture.platform.product.dto.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * ClientSearchRequest
 * Created by nuwan on 7/21/18.
 */
@Data
public class ClientSearchRequest extends BaseSearchRequest {

    private String sortProperty = "lastModifiedDate";

    //@NotBlank(message = "ClientSearchRequest.search.name.empty")
    private String name;

    @Pattern(regexp = "lastModifiedDate|name", flags = Pattern.Flag.CASE_INSENSITIVE, message = "{answerTemplateSearchRequest.sortProperty.invalid}")
    @Override
    public String getSortProperty() {
        return sortProperty;
    }
}

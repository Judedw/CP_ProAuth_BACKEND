package com.product.genuine.dto.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * ProductSearchRequest
 * Created by nuwan on 7/26/18.
 */
@Data
public class ProductSearchRequest extends BaseSearchRequest {

    private String sortProperty = "lastModifiedDate";

    //@NotBlank(message = "ClientSearchRequest.search.name.empty")
    private String code;

    @Pattern(regexp = "lastModifiedDate|name", flags = Pattern.Flag.CASE_INSENSITIVE, message = "{answerTemplateSearchRequest.sortProperty.invalid}")
    @Override
    public String getSortProperty() {
        return sortProperty;
    }
}

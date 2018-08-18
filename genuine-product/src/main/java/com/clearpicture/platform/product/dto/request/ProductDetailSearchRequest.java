package com.clearpicture.platform.product.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * ProductDetailSearchRequest
 * Created by nuwan on 8/2/18.
 *
 * Future use only
 */
@Data
public class ProductDetailSearchRequest {

    @NotBlank(message = "clientCreateRequest.clientId.empty")
    private String clientId;

    private String batchNumber;

    @Length(max = 100, message = "clientCreateRequest.code.lengthExceeds")
    private String code;

}

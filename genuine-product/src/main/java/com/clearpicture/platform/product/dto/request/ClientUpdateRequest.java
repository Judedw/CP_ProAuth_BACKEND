package com.clearpicture.platform.product.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * ClientUpdateRequest
 * Created by nuwan on 7/24/18.
 */
@Data
public class ClientUpdateRequest {

    @NotBlank(message = "clientCreateRequest.code.empty")
    @Length(max = 100, message = "clientCreateRequest.code.lengthExceeds")
    private String code;

    @NotBlank(message = "clientCreateRequest.name.empty")
    @Length(max = 100, message = "clientCreateRequest.name.lengthExceeds")
    private String name;

    @Length(max = 100, message = "clientCreateRequest.description.lengthExceeds")
    private String description;
}

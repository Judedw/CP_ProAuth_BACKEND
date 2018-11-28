package com.clearpicture.platform.product.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductAuthenticateRequest {

    @NotBlank(message = "ProductAuthenticateRequest.authCode.empty")
    private String authCode;

    private String city;

    private String province;

    private String ip;

    private String email;

    private String mobileNumber;
}

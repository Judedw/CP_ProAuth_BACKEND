package com.clearpicture.platform.product.dto.response;

import lombok.Data;

/**
 * AuthenticateResponse
 * Created by nuwan on 9/11/18.
 */
@Data
public class AuthenticateResponse {

    private String serverId;

    private String productId;

    private String title;

    private String message;

    private String authCode;
}

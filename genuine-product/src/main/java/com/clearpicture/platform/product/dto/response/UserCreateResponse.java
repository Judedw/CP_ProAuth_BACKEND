package com.clearpicture.platform.product.dto.response;

import lombok.Data;

/**
 * UserCreateResponse
 * Created by Buddhi on 11/28/18.
 */
@Data
public class UserCreateResponse {

    private String id;

    private String email;

    private String password;

    private String name;

    private String status;
}

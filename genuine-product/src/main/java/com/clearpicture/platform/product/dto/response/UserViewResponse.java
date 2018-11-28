package com.clearpicture.platform.product.dto.response;

import lombok.Data;

import javax.persistence.Lob;

/**
 * UserViewResponse
 * Created by Buddhi on 11/28/18.
 */
@Data
public class UserViewResponse {


    private String id;

    private String email;

    private String password;

    private String name;

    private String description;

    private String address_line1;

    private String address_line2;

    private String city;

    private String postal_code;

    private String country;

    private String state;

    @Lob
    private byte[] imageObject;

    private String status;
}

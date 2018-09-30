package com.clearpicture.platform.product.service;

import com.clearpicture.platform.product.dto.response.ProductAuthenticateResponse;

/**
 * AuthenticationService
 * Created by nuwan on 9/23/18.
 */
public interface AuthenticationService {
    ProductAuthenticateResponse authenticate(String code) ;
}

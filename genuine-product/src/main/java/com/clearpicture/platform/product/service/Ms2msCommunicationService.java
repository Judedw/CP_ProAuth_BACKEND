package com.clearpicture.platform.product.service;


import com.clearpicture.platform.product.dto.response.ProductAuthenticateResponse;

/**
 * Ms2msCommiunicationService
 * Created by nuwan on 9/10/18.
 */
public interface Ms2msCommunicationService {
    ProductAuthenticateResponse authenticateProduct(String s);

    ProductAuthenticateResponse authenticateEVote(String s);
}

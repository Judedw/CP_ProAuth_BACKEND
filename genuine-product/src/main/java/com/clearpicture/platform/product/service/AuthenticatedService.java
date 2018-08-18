package com.clearpicture.platform.product.service;

import org.springframework.stereotype.Service;

/**
 * AuthenticatedService
 * Created by nuwan on 8/7/18.
 */
@Service
public interface AuthenticatedService {
    Boolean authenticate(String code);
}

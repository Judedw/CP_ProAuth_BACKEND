package com.clearpicture.platform.product.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * AuthenticatedService
 * Created by nuwan on 8/7/18.
 */
@Service
public interface AuthenticatedService {
    Map<String,Object> authenticate(String code) throws Exception;
}

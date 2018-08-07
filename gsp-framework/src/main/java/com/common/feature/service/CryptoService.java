package com.common.feature.service;

/**
 * CryptoService
 * Created by nuwan on 7/21/18.
 */
public interface CryptoService {

    String encryptString(String text);

    String decryptString(String text);

    String encryptEntityId(Long value);

    Long decryptEntityId(String text);
}

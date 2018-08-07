package com.product.genuine.service;

import com.product.genuine.entity.UserKey;

/**
 * UserKey
 * Created by nuwan on 8/4/18.
 */
public interface UserKeyService {
    void saveKeysInDb(String appId, String publicKeyPEM, String privateKeyPEM);

    UserKey getPrivateKeyForAppId(String appId);
}

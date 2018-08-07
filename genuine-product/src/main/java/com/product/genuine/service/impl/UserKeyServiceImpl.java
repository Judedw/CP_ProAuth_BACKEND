package com.product.genuine.service.impl;

import com.product.genuine.entity.UserKey;
import com.product.genuine.repository.UserKeyRepository;
import com.product.genuine.service.UserKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserKeyImpl
 * Created by nuwan on 8/4/18.
 */
@Transactional
@Service
public class UserKeyServiceImpl implements UserKeyService {

    @Autowired
    private UserKeyRepository userKeyRepository;


    @Override
    public void saveKeysInDb(String appId, String publicKeyPEM, String privateKeyPEM) {
        userKeyRepository.save(new UserKey(appId,publicKeyPEM,privateKeyPEM));
    }

    @Override
    public UserKey getPrivateKeyForAppId(String appId) {
        return userKeyRepository.findByAppId(appId);
    }
}

package com.clearpicture.platform.product.service.impl;

import com.clearpicture.platform.product.entity.UserKey;
import com.clearpicture.platform.product.repository.UserKeyRepository;
import com.clearpicture.platform.product.service.UserKeyService;
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

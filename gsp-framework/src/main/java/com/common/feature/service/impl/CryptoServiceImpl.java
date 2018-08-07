package com.common.feature.service.impl;


import com.common.feature.configuration.PlatformConfigProperties;
import com.common.feature.exception.EntityIdCryptoException;
import com.common.feature.service.CryptoService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Random;

/**
 * CryptoServiceImpl
 * Created by nuwan on 7/21/18.
 */
@Service
public class CryptoServiceImpl implements CryptoService {

    @Autowired
    private PlatformConfigProperties configs;

    private TextEncryptor textEncryptor;

    private TextEncryptor entityIdEncryptor;

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 128;

    @PostConstruct
    public void init() {
        Security.setProperty("crypto.policy", "unlimited");

        // Generate an 8 character salt
        String salt = KeyGenerators.string().generateKey();
        // Generate a 128 character password
        String password = RandomStringUtils.randomAscii(256);
        System.out.println("Generated salt = " + salt);
        System.out.println("Generated password = " + password);

        //entityIdEncryptor = Encryptors.queryableText(configs.getCrypto().getPassword(),configs.getCrypto().getSalt());
        entityIdEncryptor = Encryptors.queryableText(password, salt);
    }

    @Override
    public String encryptString(String text) {
        return textEncryptor.encrypt(text);
    }

    @Override
    public String decryptString(String text) {
        return textEncryptor.decrypt(text);
    }

    @Override
    public String encryptEntityId(Long number) {
        try {
            return entityIdEncryptor.encrypt(number.toString());
        } catch (Exception e) {
            throw new EntityIdCryptoException("encryption failed! value: " + number, e);
        }

    }

    @Override
    public Long decryptEntityId(String text) {
        try {
            return Long.valueOf(entityIdEncryptor.decrypt(text));
        } catch (Exception e) {
            throw new EntityIdCryptoException("decryption failed! value: " + text, e);
        }
    }
}

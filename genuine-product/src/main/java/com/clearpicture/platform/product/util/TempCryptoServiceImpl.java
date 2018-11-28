package com.clearpicture.platform.product.util;

import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.exception.EntityIdCryptoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Random;

/**
 * TempCryptoServiceImpl
 * Created by Buddhi on 11/28/18.
 */


@Slf4j
@Transactional
@Service

public class TempCryptoServiceImpl implements TempCryptoService{
    @Autowired
    private PlatformConfigProperties configs;
    private TextEncryptor entityIdEncryptor;
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 128;

    public TempCryptoServiceImpl() {
    }

    @PostConstruct
    public void init() {
        Security.setProperty("crypto.policy", "unlimited");
        this.entityIdEncryptor = Encryptors.queryableText(this.configs.getCrypto().getPassword(), this.configs.getCrypto().getSalt());
    }



    @Override
    public String encryptUserPassword(String text) {
        try {
            System.out.println("Crypto encrypt password = " + this.configs.getCrypto().getPassword());
            System.out.println("Crypto encrypt salt = " + this.configs.getCrypto().getSalt());
            return this.entityIdEncryptor.encrypt(text);
        } catch (Exception var3) {
            throw new EntityIdCryptoException("encryption failed! value: " + text, var3);
        }
    }

    @Override
    public String decryptUserPassword(String text) {
        try {
            System.out.println("Crypto decrypt password = " + this.configs.getCrypto().getPassword());
            System.out.println("Crypto decrypt salt = " + this.configs.getCrypto().getSalt());
            return this.entityIdEncryptor.decrypt(text);
        } catch (Exception var3) {
            throw new EntityIdCryptoException("decryption failed! value: " + text, var3);
        }
    }

}

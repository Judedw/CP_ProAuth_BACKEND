package com.clearpicture.platform.product.util;


/**
 * TempCryptoService
 * Created by Buddhi on 11/28/18.
 */


public interface TempCryptoService {

    String encryptUserPassword(String text);

    String decryptUserPassword(String text);

}

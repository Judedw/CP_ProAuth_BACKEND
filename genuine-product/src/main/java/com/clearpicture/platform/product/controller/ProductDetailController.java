package com.clearpicture.platform.product.controller;


import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.product.entity.ProductDetail;
import com.clearpicture.platform.product.service.ProductDetailService;
import com.clearpicture.platform.response.wrapper.ListResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.product.dto.response.ProductDetailResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductDetailController
 * Created by nuwan on 8/2/18.
 */
@Slf4j
@RestController
public class ProductDetailController {

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private PlatformConfigProperties configs;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private ModelMapper modelMapper;

    private TextEncryptor textEncryptor;

    private BytesEncryptor bytesEncryptor;

    private static final String UNICODE_FORMAT = "ISO-8859-1";

    @PostConstruct
    public void init() {
        Security.setProperty("crypto.policy", "unlimited");
        //textEncryptor = Encryptors.text(configs.getCrypto().getPassword(), configs.getCrypto().getSalt());
        //String salt = KeyGenerators.string().generateKey();
        //textEncryptor = Encryptors.text("password", "5c0744940b5c369b");
        //bytesEncryptor = Encryptors.standard("password", "5c0744940b5c369b");
        //KeyGenerators.secureRandom(16), CipherAlgorithm.GCM
        bytesEncryptor = Encryptors.stronger(configs.getCrypto().getPassword(),configs.getCrypto().getSalt());



    }

    @GetMapping("${app.endpoint.productDetails}")
    public ResponseEntity<ListResponseWrapper<ProductDetailResponse>> retrieveList(@PathVariable String productId) throws GeneralSecurityException, IOException {

        //ProductDetailSearchCriteria criteria = modelMapper.map(request,ProductDetailSearchCriteria.class);
        Long id = cryptoService.decryptEntityId(productId);

        List<ProductDetail> results = productDetailService.retrieveList(id);

        List<ProductDetailResponse> productDetails =  modelMapper.map(results,ProductDetailResponse.class);

        List<ProductDetailResponse> detailResponses = new ArrayList<>();

        productDetails.forEach(productDetail -> {
            ProductDetailResponse response = new ProductDetailResponse();
            try {
                byte[] encVal = bytesEncryptor.encrypt(productDetail.getAuthenticationCode().getBytes(UNICODE_FORMAT));
                response.setAuthenticationCode(Hex.encodeHexString(encVal));
                //response.setAuthCode(encrypt(productDetail.getAuthenticationCode()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            detailResponses.add(response);

        });
        log.info("detailResponses ------->"+detailResponses.size());

        return new ResponseEntity<ListResponseWrapper<ProductDetailResponse>>(new ListResponseWrapper<ProductDetailResponse>(detailResponses), HttpStatus.OK);
    }

    /*@GetMapping("${app.endpoint.productAuthenticate}")
    public ResponseEntity<SimpleResponseWrapper<ProductAuthenticateResponse>> authenticate(@RequestParam String authCode) {

        //ProductDetailSearchCriteria criteria = modelMapper.map(request,ProductDetailSearchCriteria.class);
        ProductAuthenticateResponse productAuthenticateResponse = new ProductAuthenticateResponse();
        Boolean result = false;
        try {
            result = productDetailService.authenticate(new String(bytesEncryptor.decrypt(Hex.decodeHex(authCode))));
        } catch (DecoderException e) {
            productAuthenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
        }

        if(result) {
            productAuthenticateResponse.setMessage(configs.getAuthenticate().getSuccessMessage());
        } else {
            productAuthenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
        }

        //List<ProductAuthenticateResponse> productDetails =  modelMapper.map(result,ProductAuthenticateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ProductAuthenticateResponse>>(new SimpleResponseWrapper<ProductAuthenticateResponse>(productAuthenticateResponse), HttpStatus.OK);
    }*/


    /*public byte[] decrypt(byte[] rawEncryptionKey, byte[] encryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException {
        byte[] iv = null;
        byte[] encrypted = null;
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedData);

            int ivLength = byteBuffer.get();
            iv = new byte[ivLength];
            byteBuffer.get(iv);
            encrypted = new byte[byteBuffer.remaining()];
            byteBuffer.get(encrypted);

            final Cipher cipherDec = getCipher();
            cipherDec.init(Cipher.DECRYPT_MODE, new SecretKeySpec(rawEncryptionKey, "AES"), new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            return cipherDec.doFinal(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }*/

    /*public byte[] encrypt(byte[] rawEncryptionKey, byte[] rawData)  {
        if (rawEncryptionKey.length < 16) {
            throw new IllegalArgumentException("key length must be longer than 16 bytes");
        }

        byte[] iv = null;
        byte[] encrypted = null;
        try {
            iv = new byte[IV_LENGTH_BYTE];
            secureRandom.nextBytes(iv);

            final Cipher cipherEnc = getCipher();
            cipherEnc.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(rawEncryptionKey, "AES"), new GCMParameterSpec(TAG_LENGTH_BIT, iv));



            encrypted = cipherEnc.doFinal(rawData);

            ByteBuffer byteBuffer = ByteBuffer.allocate(1 + iv.length + encrypted.length);
            byteBuffer.put((byte) iv.length);
            byteBuffer.put(iv);
            byteBuffer.put(encrypted);
            return byteBuffer.array();
        } catch (Exception e) {
           return null;
        }
    }*/

    /*private Cipher getCipher() {
        if (cipher == null) {
            try {
                if (provider != null) {
                    cipher = Cipher.getInstance(ALGORITHM, provider);
                } else {
                    cipher = Cipher.getInstance(ALGORITHM);
                }
            } catch (Exception e) {
                throw new IllegalStateException("could not get cipher instance", e);
            }
        }
        return cipher;
    }*/

    /*public static String encrypt(String data) throws Exception {

        String encryptionKey = "asdfqaqwsaerdqsw";


        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes(UNICODE_FORMAT), "AES");
        final Cipher c = Cipher.getInstance("AES", "SunJCE");

        c.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes(UNICODE_FORMAT)));


        byte[] encVal = c.doFinal(data.getBytes("UTF8"));

        //String encryptedValue = Hex.toHexString(encVal);
        return encVal.toString();
    }*/

    /*public static String decrypt(String encryptedData) throws Exception {
        String encryptionKey = "asdfqaqwsaerdqsw";
        Key key = new SecretKeySpec(encryptionKey.getBytes(UNICODE_FORMAT), "AES");
        final Cipher c = Cipher.getInstance("AES", "SunJCE");

        c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes(UNICODE_FORMAT)));
        byte[] ba = Hex.decode(encryptedData);

        byte[] encVal = c.doFinal(ba);

        return new String (encVal);

    }*/
}

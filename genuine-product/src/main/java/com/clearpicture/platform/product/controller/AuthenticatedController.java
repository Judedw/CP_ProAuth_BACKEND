package com.clearpicture.platform.product.controller;


import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.product.dto.response.ProductAuthenticateResponse;
import com.clearpicture.platform.product.service.AuthenticatedService;
import com.clearpicture.platform.product.service.ProductDetailService;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.Security;

/**
 * AutheniticatedController
 * Created by nuwan on 8/7/18.
 */
@RestController
public class AuthenticatedController {

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private AuthenticatedService authenticatedService;

    private BytesEncryptor bytesEncryptor;

    @Autowired
    private PlatformConfigProperties configs;

    @PostConstruct
    public void init() {
        Security.setProperty("crypto.policy", "unlimited");
        //textEncryptor = Encryptors.text(configs.getCrypto().getPassword(), configs.getCrypto().getSalt());
        //String salt = KeyGenerators.string().generateKey();
        //textEncryptor = Encryptors.text("password", "5c0744940b5c369b");
        //bytesEncryptor = Encryptors.standard("password", "5c0744940b5c369b");
        //KeyGenerators.secureRandom(16), CipherAlgorithm.GCM
        bytesEncryptor = Encryptors.stronger(configs.getCrypto().getPassword(), configs.getCrypto().getSalt());



    }

    @GetMapping("${app.endpoint.productAuthenticate}")
    public ResponseEntity<SimpleResponseWrapper<ProductAuthenticateResponse>> authenticate(@RequestParam String authCode) {
        //ProductDetailSearchCriteria criteria = modelMapper.map(request,ProductDetailSearchCriteria.class);
        ProductAuthenticateResponse productAuthenticateResponse = new ProductAuthenticateResponse();
        Boolean result = Boolean.FALSE;
        Boolean checkAllReadyAuthenticated = Boolean.FALSE;

        try {
            checkAllReadyAuthenticated = authenticatedService.authenticate(new String(bytesEncryptor.decrypt(Hex.decodeHex(authCode))));

            //result = productDetailService.authenticate(new String(bytesEncryptor.decrypt(Hex.decodeHex(authCode))));
        } catch (Exception e) {
            e.printStackTrace();
            productAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleReject());
            productAuthenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
        }

        if(checkAllReadyAuthenticated) {
            productAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleSuccess());
            productAuthenticateResponse.setMessage(configs.getAuthenticate().getSuccessMessage());
        } else {
            productAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleReject());
            productAuthenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
        }

        //List<ProductAuthenticateResponse> productDetails =  modelMapper.map(result,ProductAuthenticateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ProductAuthenticateResponse>>(new SimpleResponseWrapper<ProductAuthenticateResponse>(productAuthenticateResponse), HttpStatus.OK);
    }
}
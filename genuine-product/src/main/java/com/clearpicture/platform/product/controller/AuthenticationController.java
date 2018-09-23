package com.clearpicture.platform.product.controller;

import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.product.dto.response.AuthenticateResponse;
import com.clearpicture.platform.product.dto.response.ProductAuthenticateResponse;
import com.clearpicture.platform.product.service.AuthenticationService;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController
 * Created by nuwan on 9/10/18.
 */
@Slf4j
@RestController
public class AuthenticationController {

    @Autowired
    private PlatformConfigProperties configs;

    @Autowired
    private AuthenticationService authenticatedService;

    @GetMapping("${app.endpoint.authenticate}")
    public ResponseEntity<SimpleResponseWrapper<AuthenticateResponse>> authenticate(@RequestParam String authCode) {
        log.debug("Authentication Start {}",authCode);
        AuthenticateResponse authenticateResponse = new AuthenticateResponse();
        ProductAuthenticateResponse productAuthenticateResponse=null;
        try {
            productAuthenticateResponse = authenticatedService.authenticate((authCode));
        }  catch (DecoderException de) {
            de.printStackTrace();
            authenticateResponse.setTitle(configs.getAuthenticate().getTitleReject());
            authenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
            return new ResponseEntity<SimpleResponseWrapper<AuthenticateResponse>>(new SimpleResponseWrapper<AuthenticateResponse>(authenticateResponse), HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            authenticateResponse.setTitle(configs.getAuthenticate().getTitleReject());
            authenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
            return new ResponseEntity<SimpleResponseWrapper<AuthenticateResponse>>(new SimpleResponseWrapper<AuthenticateResponse>(authenticateResponse), HttpStatus.OK);
        }

        if(productAuthenticateResponse != null) {
            authenticateResponse.setTitle(configs.getAuthenticate().getTitleSuccess());
            authenticateResponse.setMessage(configs.getAuthenticate().getSuccessMessage());
            authenticateResponse.setServerId(productAuthenticateResponse.getContent().getSurveyId());
        } else {
            authenticateResponse.setTitle(configs.getAuthenticate().getTitleReject());
            authenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
        }
        log.debug("Authentication End {}",authCode);
        return new ResponseEntity<SimpleResponseWrapper<AuthenticateResponse>>(new SimpleResponseWrapper<AuthenticateResponse>(authenticateResponse), HttpStatus.OK);
    }
}

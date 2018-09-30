package com.clearpicture.platform.product.controller;

import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.product.dto.response.AuthenticateResponse;
import com.clearpicture.platform.product.dto.response.ProductAuthenticateResponse;
import com.clearpicture.platform.product.service.AuthenticationService;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Authentication Start {}",authCode);
        AuthenticateResponse authenticateResponse = new AuthenticateResponse();
        ProductAuthenticateResponse productAuthenticateResponse=null;
        productAuthenticateResponse = authenticatedService.authenticate((authCode));
        authenticateResponse.setTitle(productAuthenticateResponse.getContent().getTitle());
        authenticateResponse.setMessage(productAuthenticateResponse.getContent().getMessage());
        authenticateResponse.setServerId(productAuthenticateResponse.getContent().getSurveyId());
        log.info("Authentication End {}",authCode);
        return new ResponseEntity<SimpleResponseWrapper<AuthenticateResponse>>(new SimpleResponseWrapper<AuthenticateResponse>(authenticateResponse), HttpStatus.OK);
    }
}

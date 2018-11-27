package com.clearpicture.platform.product.controller;

import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.product.dto.request.ProductAuthenticateRequest;
import com.clearpicture.platform.product.dto.response.AuthenticateResponse;
import com.clearpicture.platform.product.dto.response.ProductAuthenticateResponse;
import com.clearpicture.platform.product.entity.AuthenticatedCustomer;
import com.clearpicture.platform.product.service.AuthenticationService;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper modelMapper;

    @GetMapping("${app.endpoint.authenticate}")
    public ResponseEntity<SimpleResponseWrapper<AuthenticateResponse>> authenticate(@RequestParam String authCode) {
        log.info("Authentication Start {}",authCode);
        AuthenticateResponse authenticateResponse = new AuthenticateResponse();
        ProductAuthenticateResponse productAuthenticateResponse = authenticatedService.authenticate((authCode));

        if(productAuthenticateResponse != null && productAuthenticateResponse.getContent() != null) {
            authenticateResponse = modelMapper.map(productAuthenticateResponse.getContent(),AuthenticateResponse.class);
        }

        /*authenticateResponse.setTitle(productAuthenticateResponse.getContent().getTitle());
        authenticateResponse.setMessage(productAuthenticateResponse.getContent().getMessage());
        authenticateResponse.setServerId(productAuthenticateResponse.getContent().getSurveyId());
        authenticateResponse.setProductId(productAuthenticateResponse.getContent().getProductId());*/
        log.info("Authentication End {}",authCode);
        return new ResponseEntity<SimpleResponseWrapper<AuthenticateResponse>>(new SimpleResponseWrapper<AuthenticateResponse>(authenticateResponse), HttpStatus.OK);
    }

    @PostMapping("${app.endpoint.authenticateWithCustomer}")
    public ResponseEntity<SimpleResponseWrapper<AuthenticateResponse>> authenticateProduct(@Validated @RequestBody ProductAuthenticateRequest request) {
        log.info("Authentication Start {}",request.getAuthCode());

        AuthenticatedCustomer authenticatedCustomer = modelMapper.map(request,AuthenticatedCustomer.class);
        AuthenticateResponse authenticateResponse = new AuthenticateResponse();
        ProductAuthenticateResponse productAuthenticateResponse = authenticatedService.authenticateWithCustomer(request.getAuthCode(),authenticatedCustomer);
        if(productAuthenticateResponse != null && productAuthenticateResponse.getContent() != null) {
            authenticateResponse = modelMapper.map(productAuthenticateResponse.getContent(),AuthenticateResponse.class);
        }
        /*authenticateResponse.setTitle(productAuthenticateResponse.getContent().getTitle());
        authenticateResponse.setMessage(productAuthenticateResponse.getContent().getMessage());
        authenticateResponse.setServerId(productAuthenticateResponse.getContent().getSurveyId());
        authenticateResponse.setProductId(productAuthenticateResponse.getContent().getProductId());*/


        log.info("Authentication End {}",request.getAuthCode());

        return new ResponseEntity<SimpleResponseWrapper<AuthenticateResponse>>(new SimpleResponseWrapper<AuthenticateResponse>(authenticateResponse), HttpStatus.OK);
    }
}

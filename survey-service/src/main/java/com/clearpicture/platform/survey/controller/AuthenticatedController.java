package com.clearpicture.platform.survey.controller;


import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.response.EVoteAuthenticateResponse;
import com.clearpicture.platform.survey.service.AuthenticatedService;
import com.clearpicture.platform.survey.service.EVoteDetailService;
import com.clearpicture.platform.survey.service.EVoteService;
import com.clearpicture.platform.util.AuthenticatedConstant;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.Map;

/**
 * AutheniticatedController
 * Created by nuwan on 8/7/18.
 */
@Slf4j
@RestController
public class AuthenticatedController {

    @Autowired
    private EVoteDetailService eVoteDetailService;

    @Autowired
    private EVoteService eVoteService;

    @Autowired
    private AuthenticatedService authenticatedService;

    private BytesEncryptor bytesEncryptor;

    @Autowired
    private PlatformConfigProperties configs;

    @Autowired
    private CryptoService cryptoService;

    @PostConstruct
    public void init() {
        Security.setProperty("crypto.policy", "unlimited");
        bytesEncryptor = Encryptors.stronger(configs.getCrypto().getPassword(), configs.getCrypto().getSalt());
    }

    @GetMapping("${app.endpoint.eVoteAuthenticate}")
    public ResponseEntity<SimpleResponseWrapper<EVoteAuthenticateResponse>> authenticate(@RequestParam String authCode) {

        log.info("authCode-->"+authCode);

        EVoteAuthenticateResponse evoteAuthenticateResponse = new EVoteAuthenticateResponse();
        Long surveyId =0L;
        Boolean checkAllReadyAuthenticated = Boolean.FALSE;
        Map<String,Object> authenticatedMap = new HashMap<>();
        try {
            authenticatedMap = authenticatedService.authenticate((authCode));
            checkAllReadyAuthenticated = (Boolean) authenticatedMap.get(AuthenticatedConstant.AUTH_STATUS);
            surveyId = (Long) authenticatedMap.get(AuthenticatedConstant.SURVEY_ID);
            log.info("surveyId {}",surveyId);
        } catch (Exception e) {
            e.printStackTrace();
            evoteAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleReject());
            evoteAuthenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
        }
        log.info("checkAllReadyAuthenticated {}",checkAllReadyAuthenticated);
        if(checkAllReadyAuthenticated) {
            evoteAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleSuccess());
            evoteAuthenticateResponse.setMessage(configs.getAuthenticate().getSuccessMessage());
            if(surveyId != null)
                evoteAuthenticateResponse.setSurveyId(cryptoService.encryptEntityId(surveyId));
        } else {
            evoteAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleReject());
            evoteAuthenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
        }


        return new ResponseEntity<SimpleResponseWrapper<EVoteAuthenticateResponse>>(new SimpleResponseWrapper<EVoteAuthenticateResponse>(evoteAuthenticateResponse), HttpStatus.OK);
    }
}

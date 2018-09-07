package com.clearpicture.platform.survey.controller;


import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.response.EVoteAuthenticateResponse;
import com.clearpicture.platform.survey.entity.Authenticated;
import com.clearpicture.platform.survey.entity.EVote;
import com.clearpicture.platform.survey.service.AuthenticatedService;
import com.clearpicture.platform.survey.service.EVoteDetailService;
import com.clearpicture.platform.survey.service.EVoteService;
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

        EVoteAuthenticateResponse evoteAuthenticateResponse = new EVoteAuthenticateResponse();
        Boolean result = Boolean.FALSE;
        Boolean checkAllReadyAuthenticated = Boolean.FALSE;

        try {
            Authenticated authenticated = authenticatedService.authenticate(new String(bytesEncryptor.decrypt(Hex.decodeHex(authCode))));

            if(authenticated != null) {
                EVote eVote = authenticated.getEVoteDetail().getEVote();
                if(eVote !=null)
                    evoteAuthenticateResponse.setSurveyId(cryptoService.encryptEntityId(eVote.getSurveyId()));
                evoteAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleSuccess());
                evoteAuthenticateResponse.setMessage(configs.getAuthenticate().getSuccessMessage());
            } else {
                evoteAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleReject());
                evoteAuthenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            evoteAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleReject());
            evoteAuthenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());

        }


        return new ResponseEntity<SimpleResponseWrapper<EVoteAuthenticateResponse>>(new SimpleResponseWrapper<EVoteAuthenticateResponse>(evoteAuthenticateResponse), HttpStatus.OK);
    }
}

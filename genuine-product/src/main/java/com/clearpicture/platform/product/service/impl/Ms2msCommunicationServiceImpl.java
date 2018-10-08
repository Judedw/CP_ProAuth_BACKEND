package com.clearpicture.platform.product.service.impl;



import com.clearpicture.platform.componant.RestApiClient;
import com.clearpicture.platform.product.configuration.ConfigProperties;
import com.clearpicture.platform.product.dto.response.ProductAuthenticateResponse;
import com.clearpicture.platform.product.dto.response.external.SurveyValidateResponse;
import com.clearpicture.platform.product.service.Ms2msCommunicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Ms2msCommunicationServiceImpl
 * Created by nuwan on 9/10/18.
 */
@Slf4j
@Service("ms2msCommunicationService")
public class Ms2msCommunicationServiceImpl implements Ms2msCommunicationService {

    @Autowired
    private RestApiClient restClient;

    @Autowired
    private ConfigProperties configProperties;


    @Override
    public ProductAuthenticateResponse authenticateProduct(String authCode) {
        Map<String, String> params = new HashMap<>();
        params.put("authCode", authCode);
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(configProperties.getExternalEndpointProduct().getAuthProductDetail());
        urlBuilder.append("?authCode="+authCode);
        log.info("urlBuilder URL -->" + urlBuilder.toString());
        ProductAuthenticateResponse productAuthenticateResponse = restClient.getRestTemplate().getForObject(urlBuilder.toString(), ProductAuthenticateResponse.class);

        return productAuthenticateResponse;
    }

    @Override
    public ProductAuthenticateResponse authenticateEVote(String authCode) {

        Map<String, String> params = new HashMap<>();
        params.put("authCode", authCode);
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(configProperties.getExternalEndpointEVote().getAuthEVoteDetail());
        urlBuilder.append("?authCode="+authCode);
        log.info("urlBuilder URL -->" + urlBuilder.toString());
        ProductAuthenticateResponse productAuthenticateResponse = restClient.getRestTemplate().getForObject(urlBuilder.toString(), ProductAuthenticateResponse.class);
        log.info("productAuthenticateResponse -->" +productAuthenticateResponse);
        return productAuthenticateResponse;

    }

    @Override
    public Boolean validateSurvey(String surveyId) {

        Map<String, String> params = new HashMap<>();
        params.put("surveyId", surveyId);
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(configProperties.getExternalEndpointEVote().getValidateSurvey());

        urlBuilder.append("?surveyId="+surveyId);
        log.info("urlBuilder URL -->" + urlBuilder.toString());
        SurveyValidateResponse surveyValidateResponse = restClient.getRestTemplate().getForObject(urlBuilder.toString(), SurveyValidateResponse.class);
        log.info("surveyValidateResponse -->" +surveyValidateResponse);

        if(surveyValidateResponse != null) {
            return surveyValidateResponse.getContent().getIsValid();
        } else {
            return false;
        }
    }
}

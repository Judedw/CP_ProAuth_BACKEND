package com.clearpicture.platform.survey.service.impl;



import com.clearpicture.platform.componant.RestApiClient;
import com.clearpicture.platform.survey.configuration.ConfigProperties;
import com.clearpicture.platform.survey.dto.response.external.ClientValidateResponse;
import com.clearpicture.platform.survey.service.Ms2msCommunicationService;
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
    public Boolean validateClient(String clientId) {

        Map<String, String> params = new HashMap<>();
        params.put("clientId", clientId);
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(configProperties.getExternalEndpointProduct().getValidateClient());

        urlBuilder.append("?clientId="+clientId);
        log.info("urlBuilder URL -->" + urlBuilder.toString());
        ClientValidateResponse clientValidateResponse = restClient.getRestTemplate().getForObject(urlBuilder.toString(), ClientValidateResponse.class);
        log.info("surveyValidateResponse -->" +clientValidateResponse);

        if(clientValidateResponse != null) {
            return clientValidateResponse.getContent().getIsValid();
        } else {
            return false;
        }
    }
}

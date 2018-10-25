package com.clearpicture.platform.survey.controller;


import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.request.FutureSurveyCreateRequest;
import com.clearpicture.platform.survey.dto.request.FutureSurveyCreateResponse;
import com.clearpicture.platform.survey.entity.FutureSurvey;
import com.clearpicture.platform.survey.service.FutureSurveyService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by Raveen -  18/10/24
 * FutureSurveyController - Handling every advanced surveys & e votes
 */

@RestController
public class FutureSurveyController {


    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper modelMapper;

    @Autowired
    private FutureSurveyService futureSurveyService;

    @Autowired
    private CryptoService cryptoService;

    @PostMapping(value = "${app.endpoint.futureSurveyCreate}")
    public ResponseEntity<SimpleResponseWrapper<FutureSurveyCreateResponse>> create(
            @RequestBody FutureSurveyCreateRequest futureSurveyCreateRequest
    ) {

        FutureSurvey futureSurvey = modelMapper.map(futureSurveyCreateRequest, FutureSurvey.class);

        FutureSurvey savedSurvey = futureSurveyService.save(futureSurvey);

        FutureSurveyCreateResponse futureSurveyCreateResponse = modelMapper.map(savedSurvey, FutureSurveyCreateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<FutureSurveyCreateResponse>>(new SimpleResponseWrapper<FutureSurveyCreateResponse>(futureSurveyCreateResponse), HttpStatus.CREATED);
    }

}

package com.clearpicture.platform.survey.controller;

import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.survey.dto.request.SurveyCreateRequest;
import com.clearpicture.platform.survey.dto.response.SurveyCreateResponse;
import com.clearpicture.platform.survey.entity.Survey;
import com.clearpicture.platform.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SurveyController
 * Created by nuwan on 8/17/18.
 */
@RestController
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper modelMapper;

    @PostMapping("${app.endpoint.surveysCreate}")
    public ResponseEntity<SimpleResponseWrapper<SurveyCreateResponse>> create(@Validated @RequestBody SurveyCreateRequest request) {

        Survey survey = modelMapper.map(request,Survey.class);

        Survey savedSurvey = surveyService.save(survey);

        SurveyCreateResponse response = modelMapper.map(savedSurvey,SurveyCreateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<SurveyCreateResponse>>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
    }
}

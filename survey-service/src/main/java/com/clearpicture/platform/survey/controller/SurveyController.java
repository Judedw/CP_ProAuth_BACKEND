package com.clearpicture.platform.survey.controller;

import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.response.wrapper.PagingListResponseWrapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.request.SurveyCreateRequest;
import com.clearpicture.platform.survey.dto.request.SurveySearchRequest;
import com.clearpicture.platform.survey.dto.request.SurveyUpdateRequest;
import com.clearpicture.platform.survey.dto.response.SurveyCreateResponse;
import com.clearpicture.platform.survey.dto.response.SurveyDeleteResponse;
import com.clearpicture.platform.survey.dto.response.SurveySearchResponse;
import com.clearpicture.platform.survey.dto.response.SurveyUpdateResponse;
import com.clearpicture.platform.survey.dto.response.SurveyViewResponse;
import com.clearpicture.platform.survey.entity.Survey;
import com.clearpicture.platform.survey.entity.criteria.SurveySearchCriteria;
import com.clearpicture.platform.survey.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * SurveyController
 * Created by nuwan on 8/17/18.
 */
@Slf4j
@RestController
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper modelMapper;

    @Autowired
    private CryptoService cryptoService;

    @PostMapping("${app.endpoint.surveysCreate}")
    public ResponseEntity<SimpleResponseWrapper<SurveyCreateResponse>> create
            (@Validated @RequestBody SurveyCreateRequest request) {

        Survey survey = modelMapper.map(request,Survey.class);

        Survey savedSurvey = surveyService.save(survey);

        SurveyCreateResponse response = modelMapper.map(savedSurvey,SurveyCreateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<SurveyCreateResponse>>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @GetMapping("${app.endpoint.surveysSearch}")
    public ResponseEntity<PagingListResponseWrapper<SurveySearchResponse>> retrieve
            (@Validated SurveySearchRequest request) {

        SurveySearchCriteria criteria = modelMapper.map(request,SurveySearchCriteria.class);

        Page<Survey> results = surveyService.search(criteria);

        List<SurveySearchResponse> clients =  modelMapper.map(results.getContent(), SurveySearchResponse.class);

        PagingListResponseWrapper.Pagination pagination = new PagingListResponseWrapper.Pagination(
                results.getNumber() + 1, results.getSize(), results.getTotalPages(), results.getTotalElements());

        return new ResponseEntity<PagingListResponseWrapper<SurveySearchResponse>>(new PagingListResponseWrapper<SurveySearchResponse>(clients,pagination),HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.surveysView}")
    public ResponseEntity<SimpleResponseWrapper<SurveyViewResponse>> retrieve(@PathVariable String id) {

        Long surveyId = cryptoService.decryptEntityId(id);

        Survey retrievedSurvey = surveyService.retrieve(surveyId);

        SurveyViewResponse response = modelMapper.map(retrievedSurvey,SurveyViewResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<SurveyViewResponse>>(new SimpleResponseWrapper<SurveyViewResponse>(response),HttpStatus.OK);

    }

    @PutMapping("${app.endpoint.surveysUpdate}")
    public ResponseEntity<SimpleResponseWrapper<SurveyUpdateResponse>> update(@PathVariable String id,
                                                                              @Validated @RequestBody SurveyUpdateRequest request) {

        Long surveyId = cryptoService.decryptEntityId(id);

        Survey survey = modelMapper.map(request,Survey.class);

        survey.setId(surveyId);

        Survey updatedSurvey = surveyService.update(survey);

        SurveyUpdateResponse response = modelMapper.map(updatedSurvey,SurveyUpdateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<SurveyUpdateResponse>>(new SimpleResponseWrapper<SurveyUpdateResponse>(response),HttpStatus.OK);


    }

    @DeleteMapping("${app.endpoint.surveysDelete}")
    public ResponseEntity<SimpleResponseWrapper<SurveyDeleteResponse>> delete(@PathVariable String id) {

        Long surveyId = cryptoService.decryptEntityId(id);

        Survey survey = surveyService.delete(surveyId);

        SurveyDeleteResponse response = modelMapper.map(survey.getId(),SurveyDeleteResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<SurveyDeleteResponse>>(new SimpleResponseWrapper<SurveyDeleteResponse>(response),HttpStatus.OK);
    }
}

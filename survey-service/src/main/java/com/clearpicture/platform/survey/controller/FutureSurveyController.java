package com.clearpicture.platform.survey.controller;


import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.response.wrapper.PagingListResponseWrapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.request.*;
import com.clearpicture.platform.survey.dto.response.FutureSurveyCreateResponse;
import com.clearpicture.platform.survey.dto.response.FutureSurveyDeleteResponse;
import com.clearpicture.platform.survey.dto.response.FutureSurveySearchResponse;
import com.clearpicture.platform.survey.dto.response.FutureSurveyUpdateResponse;
import com.clearpicture.platform.survey.entity.FutureSurvey;
import com.clearpicture.platform.survey.entity.criteria.FutureSurveySearchCriteria;
import com.clearpicture.platform.survey.service.FutureSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "${app.endpoint.futureSurveySearch}")
    public ResponseEntity<PagingListResponseWrapper<FutureSurveySearchResponse>> retrieve(@Validated FutureSurveySearchRequest request) {

        FutureSurveySearchCriteria criteria = modelMapper.map(request, FutureSurveySearchCriteria.class);

        Page<FutureSurvey> results = futureSurveyService.search(criteria);

        List<FutureSurveySearchResponse> futureSurveys = modelMapper.map(results.getContent(), FutureSurveySearchResponse.class);


        PagingListResponseWrapper.Pagination pagination = new PagingListResponseWrapper.Pagination(
                results.getNumber() + 1, results.getSize(), results.getTotalPages(), results.getTotalElements());

        return new ResponseEntity<PagingListResponseWrapper<FutureSurveySearchResponse>>(new PagingListResponseWrapper<FutureSurveySearchResponse>(futureSurveys, pagination), HttpStatus.OK);

    }

    @PutMapping(value = "${app.endpoint.futureSurveyUpdate}")
    public ResponseEntity<SimpleResponseWrapper<FutureSurveyUpdateResponse>> update(@PathVariable String id, @RequestBody FutureSurveyUpdateRequest request) {

        Long surveyId = cryptoService.decryptEntityId(id);
        System.out.println("SURVEY ID :  " + surveyId);
        FutureSurvey fSurvey = modelMapper.map(request, FutureSurvey.class);
        fSurvey.setId(surveyId);

        FutureSurvey updateSurvey = futureSurveyService.update(fSurvey);

        FutureSurveyUpdateResponse response = modelMapper.map(updateSurvey, FutureSurveyUpdateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<FutureSurveyUpdateResponse>>(new SimpleResponseWrapper<FutureSurveyUpdateResponse>(response), HttpStatus.OK);
    }

    @DeleteMapping("${app.endpoint.futureSurveyDelete}")
    public ResponseEntity<SimpleResponseWrapper<FutureSurveyDeleteResponse>> delete(@PathVariable String id) {
        Long surveyId = cryptoService.decryptEntityId(id);
        FutureSurvey fSurvey = futureSurveyService.delete(surveyId);
        FutureSurveyDeleteResponse response = modelMapper.map(fSurvey.getId(), FutureSurveyDeleteResponse.class);
        return new ResponseEntity<SimpleResponseWrapper<FutureSurveyDeleteResponse>>(new SimpleResponseWrapper<FutureSurveyDeleteResponse>(response), HttpStatus.OK);
    }

}

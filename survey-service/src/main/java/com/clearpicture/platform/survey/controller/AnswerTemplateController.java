package com.clearpicture.platform.survey.controller;

import com.clearpicture.platform.dto.request.GeneralSuggestionRequest;
import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.response.wrapper.ListResponseWrapper;
import com.clearpicture.platform.response.wrapper.PagingListResponseWrapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.request.AnswerTeamplateSearchRequest;
import com.clearpicture.platform.survey.dto.request.AnswerTemplateCreateRequest;
import com.clearpicture.platform.survey.dto.request.AnswerTemplateUpdateRequest;
import com.clearpicture.platform.survey.dto.response.AnswerTeamplateViewResponse;
import com.clearpicture.platform.survey.dto.response.AnswerTemplateCreateResponse;
import com.clearpicture.platform.survey.dto.response.AnswerTemplateDeleteResponse;
import com.clearpicture.platform.survey.dto.response.AnswerTemplateSearchResponse;
import com.clearpicture.platform.survey.dto.response.AnswerTemplateSuggestionResponse;
import com.clearpicture.platform.survey.dto.response.AnswerTemplateUpdateResponse;
import com.clearpicture.platform.survey.entity.Answer;
import com.clearpicture.platform.survey.entity.AnswerTemplate;
import com.clearpicture.platform.survey.entity.criteria.AnswerTemplateCriteria;
import com.clearpicture.platform.survey.service.AnswerTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

/**
 * AnswerTemplateController
 * Created by nuwan on 8/18/18.
 */
@RestController
public class AnswerTemplateController {

    @Autowired
    private AnswerTemplateService answerTemplateService;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("${app.endpoint.answerTemplatesCreate}")
    public ResponseEntity<SimpleResponseWrapper<AnswerTemplateCreateResponse>> create(
            @Validated @RequestBody AnswerTemplateCreateRequest request) {

        AnswerTemplate newAnswerTemplate = modelMapper.map(request, AnswerTemplate.class);

        AnswerTemplate savedAnswerTemplate = answerTemplateService.create(newAnswerTemplate);

        AnswerTemplateCreateResponse response = modelMapper.map(savedAnswerTemplate,
                AnswerTemplateCreateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<AnswerTemplateCreateResponse>>(
                new SimpleResponseWrapper<AnswerTemplateCreateResponse>(response), HttpStatus.CREATED);
    }

    @GetMapping("${app.endpoint.answerTemplatesSearch}")
    public ResponseEntity<PagingListResponseWrapper<AnswerTemplateSearchResponse>> retrieve(
            @Validated AnswerTeamplateSearchRequest request) {
        AnswerTemplateCriteria criteria = modelMapper.map(request, AnswerTemplateCriteria.class);

        Page<AnswerTemplate> results = answerTemplateService.search(criteria);

        List<AnswerTemplateSearchResponse> answerTeamplates = modelMapper.map(results.getContent(),
                AnswerTemplateSearchResponse.class);

        PagingListResponseWrapper.Pagination pagination = new PagingListResponseWrapper.Pagination(
                results.getNumber() + 1, results.getSize(), results.getTotalPages(), results.getTotalElements());

        return new ResponseEntity<PagingListResponseWrapper<AnswerTemplateSearchResponse>>(
                new PagingListResponseWrapper<AnswerTemplateSearchResponse>(answerTeamplates, pagination),
                HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.answerTemplatesView}")
    public ResponseEntity<SimpleResponseWrapper<AnswerTeamplateViewResponse>> retrieve(@PathVariable String id) {
        Long answerTemplateId = cryptoService.decryptEntityId(id);

        AnswerTemplate retrivedAnswerTemplate = answerTemplateService.retrieve(answerTemplateId);

        List<Answer> answerList = retrivedAnswerTemplate.getAnswers().stream().sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue())).collect(Collectors.toList());

        AnswerTeamplateViewResponse response = modelMapper.map(retrivedAnswerTemplate,
                AnswerTeamplateViewResponse.class);
        List<AnswerTeamplateViewResponse.Answers> answers = modelMapper.map(answerList,AnswerTeamplateViewResponse.Answers.class);
        response.setAnswers(answers);

        return new ResponseEntity<SimpleResponseWrapper<AnswerTeamplateViewResponse>>(
                new SimpleResponseWrapper<AnswerTeamplateViewResponse>(response), HttpStatus.OK);
    }

    @PutMapping("${app.endpoint.answerTemplatesUpdate}")
    public ResponseEntity<SimpleResponseWrapper<AnswerTemplateUpdateResponse>> update(@PathVariable String id,
                                                                                      @Validated @RequestBody AnswerTemplateUpdateRequest request) {

        AnswerTemplate answerTemplate = modelMapper.map(request, AnswerTemplate.class);
        Long answerTemplateId = cryptoService.decryptEntityId(id);
        answerTemplate.setId(answerTemplateId);

        AnswerTemplate updatedAnswerTemplate = answerTemplateService.update(answerTemplate);

        AnswerTemplateUpdateResponse response = modelMapper.map(updatedAnswerTemplate,
                AnswerTemplateUpdateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<AnswerTemplateUpdateResponse>>(
                new SimpleResponseWrapper<AnswerTemplateUpdateResponse>(response), HttpStatus.OK);
    }

    @DeleteMapping("${app.endpoint.answerTemplatesDelete}")
    public ResponseEntity<SimpleResponseWrapper<AnswerTemplateDeleteResponse>> delete(@PathVariable String id) {
        Long answerTemplateId = cryptoService.decryptEntityId(id);

        AnswerTemplate deleteAnswerTemplate = answerTemplateService.delete(answerTemplateId);

        AnswerTemplateDeleteResponse response = modelMapper.map(answerTemplateId, AnswerTemplateDeleteResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<AnswerTemplateDeleteResponse>>(
                new SimpleResponseWrapper<AnswerTemplateDeleteResponse>(response), HttpStatus.OK);
    }


    @GetMapping("${app.endpoint.answerTemplatesSuggestion}")
    public ResponseEntity<ListResponseWrapper<AnswerTemplateSuggestionResponse>> retrieve(GeneralSuggestionRequest request) {
        List<AnswerTemplate> answerTemplates = answerTemplateService.retrieveForSuggestions(request.getKeyword());

        List<AnswerTemplateSuggestionResponse> answerTemplatesSuggestions = modelMapper.map(answerTemplates, AnswerTemplateSuggestionResponse.class);

        return new ResponseEntity<ListResponseWrapper<AnswerTemplateSuggestionResponse>>(
                new ListResponseWrapper<AnswerTemplateSuggestionResponse>(answerTemplatesSuggestions), HttpStatus.OK);
    }

}

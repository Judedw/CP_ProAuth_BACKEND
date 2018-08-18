package com.clearpicture.platform.survey.controller;

import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.response.wrapper.PagingListResponseWrapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.request.QuestionCreateRequest;
import com.clearpicture.platform.survey.dto.request.QuestionSearchRequest;
import com.clearpicture.platform.survey.dto.request.QuestionUpdateRequest;
import com.clearpicture.platform.survey.dto.response.QuestionCreateResponse;
import com.clearpicture.platform.survey.dto.response.QuestionDeleteResponse;
import com.clearpicture.platform.survey.dto.response.QuestionSearchResponse;
import com.clearpicture.platform.survey.dto.response.QuestionUpdateResponse;
import com.clearpicture.platform.survey.dto.response.QuestionViewResponse;
import com.clearpicture.platform.survey.entity.Question;
import com.clearpicture.platform.survey.entity.criteria.QuestionCriteria;
import com.clearpicture.platform.survey.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
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

/**
 * QuestionController
 * Created by nuwan on 8/17/18.
 */
@Slf4j
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("${app.endpoint.questionsCreate}")
    public ResponseEntity<SimpleResponseWrapper<QuestionCreateResponse>> create(@Validated @RequestBody QuestionCreateRequest request) {

        Question newQuestion = modelMapper.map(request, Question.class);

        Question savedQuestion = questionService.create(newQuestion);

        QuestionCreateResponse response = modelMapper.map(savedQuestion, QuestionCreateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<QuestionCreateResponse>>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @GetMapping("${app.endpoint.questionsSearch}")
    public ResponseEntity<PagingListResponseWrapper<QuestionSearchResponse>> retrieve(
            @Validated QuestionSearchRequest request) {

        QuestionCriteria criteria = modelMapper.map(request, QuestionCriteria.class);

        Page<Question> results = questionService.search(criteria);

        List<QuestionSearchResponse> question = modelMapper.map(results.getContent(), QuestionSearchResponse.class);

        PagingListResponseWrapper.Pagination pagination = new PagingListResponseWrapper.Pagination(
                results.getNumber() + 1, results.getSize(), results.getTotalPages(), results.getTotalElements());

        return new ResponseEntity<PagingListResponseWrapper<QuestionSearchResponse>>(
                new PagingListResponseWrapper<QuestionSearchResponse>(question, pagination), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.questionsView}")
    public ResponseEntity<SimpleResponseWrapper<QuestionViewResponse>> retrieve(@PathVariable String id) {
        Long questionId = cryptoService.decryptEntityId(id);

        Question retrivedQuestion = questionService.retrieve(questionId);

        QuestionViewResponse response = modelMapper.map(retrivedQuestion, QuestionViewResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<QuestionViewResponse>>(
                new SimpleResponseWrapper<QuestionViewResponse>(response), HttpStatus.OK);
    }

    @PutMapping("${app.endpoint.questionsUpdate}")
    public ResponseEntity<SimpleResponseWrapper<QuestionUpdateResponse>> update(@PathVariable String id,
                                                                                @Validated @RequestBody QuestionUpdateRequest request) {
        Question question = modelMapper.map(request, Question.class);
        Long questionId = cryptoService.decryptEntityId(id);
        question.setId(questionId);

        Question updatedQuestion = questionService.update(question);

        QuestionUpdateResponse response = modelMapper.map(updatedQuestion, QuestionUpdateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<QuestionUpdateResponse>>(
                new SimpleResponseWrapper<QuestionUpdateResponse>(response), HttpStatus.OK);
    }

    @DeleteMapping("${app.endpoint.questionsDelete}")
    public ResponseEntity<SimpleResponseWrapper<QuestionDeleteResponse>> delete(@PathVariable String id) {
        Long questionId = cryptoService.decryptEntityId(id);

        Question deletedQuestion = questionService.delete(questionId);

        QuestionDeleteResponse response = modelMapper.map(questionId, QuestionDeleteResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<QuestionDeleteResponse>>(
                new SimpleResponseWrapper<QuestionDeleteResponse>(response), HttpStatus.OK);
    }
}

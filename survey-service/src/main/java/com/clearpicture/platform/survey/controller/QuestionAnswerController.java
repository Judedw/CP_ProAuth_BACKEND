package com.clearpicture.platform.survey.controller;

import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.request.AnswerSubmitRequest;
import com.clearpicture.platform.survey.dto.response.AnswerSubmitResponse;
import com.clearpicture.platform.survey.entity.Question;
import com.clearpicture.platform.survey.entity.QuestionAnswer;
import com.clearpicture.platform.survey.entity.criteria.QuestionAnswerCriteria;
import com.clearpicture.platform.survey.enums.QuestionStatus;
import com.clearpicture.platform.survey.service.QuestionAnswerService;
import com.clearpicture.platform.survey.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.security.Security;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * AnswerController
 * Created by nuwan on 9/7/18.
 */
@Slf4j
@RestController
public class QuestionAnswerController {

    @Autowired
    private QuestionAnswerService questionAnswerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper modelMapper;

    private BytesEncryptor bytesEncryptor;

    @Autowired
    private PlatformConfigProperties configs;

    @PostConstruct
    public void init() {
        Security.setProperty("crypto.policy", "unlimited");
        bytesEncryptor = Encryptors.stronger(configs.getCrypto().getPassword(),configs.getCrypto().getSalt());
    }


    @PostMapping("${app.endpoint.saveAnswer}")
    public ResponseEntity<SimpleResponseWrapper<AnswerSubmitResponse>> create(
            @Validated @RequestBody AnswerSubmitRequest request) throws Exception {

        log.info("request :"+request);

        log.info("request survey id:"+cryptoService.decryptEntityId(request.getSurveyId()));

        Set<QuestionAnswer> answers = new HashSet<>();
        AnswerSubmitResponse response = new AnswerSubmitResponse();
        List<QuestionAnswer> savedQuestionAnswers = new ArrayList<>();
        List<Question> allReadyAnsweredQuestions = new ArrayList<>();

        List<QuestionAnswerCriteria> questions = modelMapper.map(request.getQuestions(),QuestionAnswerCriteria.class);

        if(questions !=null && questions.size() !=0) {

            questions.forEach(question -> {

                Question dbQuestion = questionService.retrieve(question.getId());

                /*temp fix existing data issue*/
                if(dbQuestion.getStatus() == null ) {
                    dbQuestion.setStatus(QuestionStatus.NEW);
                }

                //if( !dbQuestion.getStatus().equals(QuestionStatus.ANSWERED)) {

                    log.info("ex date :"+dbQuestion.getExpirationDate());

                    if(checkSurveyExpiration(dbQuestion.getExpirationDate(), LocalDate.now())) {

                        if(question.getAnswers()!= null && question.getAnswers().size() != 0 ) {

                            question.getAnswers().forEach(answer -> {
                                if(answer.getId() != null || !answer.getFreeText().isEmpty()) {

                                    boolean answerStatus = false;
                                    //check question already answered or not
                                    if(answer.getId() != null) {
                                        answerStatus= checkQuestionAnswerStatus(question.getId(),answer.getId(),request.getAuthCode());
                                    } else {
                                        answerStatus= checkQuestionAnswerStatusWithFreeText(question.getId(),answer.getFreeText(),request.getAuthCode());
                                    }

                                    if(answerStatus) {
                                        allReadyAnsweredQuestions.add(dbQuestion);
                                    } else {
                                        QuestionAnswer newAnswer = new QuestionAnswer();
                                        if(answer.getId() != null)
                                            newAnswer.setAnswerId(answer.getId());
                                        newAnswer.setFreeText(answer.getFreeText());
                                        newAnswer.setAuthCode(decodeAuthCode(request.getAuthCode()));
                                        newAnswer.setQuestion(dbQuestion);
                                        answers.add(newAnswer);
                                    }
                                }

                            });
                        }

                        List<QuestionAnswer> savedAnswers = questionAnswerService.create(answers,dbQuestion);
                        savedQuestionAnswers.addAll(savedAnswers);


                    } else {
                        throw new ComplexValidationException("answer", "platformAnswerSubmitRequest.answer.alreadyExpired");
                    }

                /*} else {
                    allReadyAnsweredQuestions.add(dbQuestion);
                }*/
            });

        }

        response.setAlreadyAnswerQuestion(modelMapper.map(allReadyAnsweredQuestions,AnswerSubmitResponse.AlreadyAnswerQuestion.class));
        response.setNewlyAnswerQuestion(modelMapper.map(savedQuestionAnswers, AnswerSubmitResponse.NewlyAnswerQuestion.class));
        return new ResponseEntity<>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
    }

    public boolean checkSurveyExpiration(LocalDate expirationDate, LocalDate now) {
        if(expirationDate!= null) {
            boolean notExpired = now.isBefore(expirationDate);
            return notExpired;
        } else {
            return true;
        }

    }

    public boolean checkQuestionAnswerStatus(Long questionId,Long answerId,String authCode) {

        String authenticationCode = null;
        try {
            authenticationCode = new String(bytesEncryptor.decrypt(Hex.decodeHex(authCode)));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        String[] authCodeType = authenticationCode.split("/");

        return questionAnswerService.checkQuestionAnswerStatus(questionId,answerId,authCodeType[0]);

    }

    public boolean checkQuestionAnswerStatusWithFreeText(Long questionId,String answer,String authCode) {

        String authenticationCode = null;
        try {
            authenticationCode = new String(bytesEncryptor.decrypt(Hex.decodeHex(authCode)));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        String[] authCodeType = authenticationCode.split("/");

        return questionAnswerService.checkQuestionAnswerStatusWithFreeText(questionId,answer,authCodeType[0]);

    }

    public String decodeAuthCode(String authCode) {
        String authenticationCode = null;
        try {
            authenticationCode = new String(bytesEncryptor.decrypt(Hex.decodeHex(authCode)));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        String[] authCodeType = authenticationCode.split("/");

        return authCodeType[0];
    }


}

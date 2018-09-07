package com.clearpicture.platform.survey.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

                if(!dbQuestion.getStatus().equals(QuestionStatus.ANSWERED)) {

                    log.info("ex date :"+dbQuestion.getExpirationDate());

                    if(checkPulseExpiration(dbQuestion.getExpirationDate(), ZonedDateTime.now())) {

                        if(question.getAnswers()!= null && question.getAnswers().size() != 0 ) {

                            question.getAnswers().forEach(answer -> {
                                if(answer.getId() != null || !answer.getFreeText().isEmpty()) {
                                    QuestionAnswer newAnswer = new QuestionAnswer();
                                    if(answer.getId() != null)
                                        newAnswer.setAnswerId(answer.getId());
                                    newAnswer.setFreeText(answer.getFreeText());
                                    newAnswer.setQuestion(dbQuestion);
                                    answers.add(newAnswer);
                                }

                            });
                        }

                        List<QuestionAnswer> savedAnswers = questionAnswerService.create(answers,dbQuestion);
                        savedQuestionAnswers.addAll(savedAnswers);


                    } else {
                        throw new ComplexValidationException("answer", "platformAnswerSubmitRequest.answer.alreadyExpired");
                    }

                } else {
                    allReadyAnsweredQuestions.add(dbQuestion);

                    //throw new ComplexValidationException("answer", "platformAnswerSubmitRequest.answer.alreadyAnswered");
                }
            });

        }

        response.setAlreadyAnswerQuestion(modelMapper.map(allReadyAnsweredQuestions,AnswerSubmitResponse.AlreadyAnswerQuestion.class));
        response.setNewlyAnswerQuestion(modelMapper.map(savedQuestionAnswers, AnswerSubmitResponse.NewlyAnswerQuestion.class));


        //QuestionAnswerCriteria criteria = modelMapper.map(request,QuestionAnswerCriteria.class);



        /*log.info("question id :"+cryptoService.decryptEntityId(request.getQuestionId()));

        Question question = questionService.retrieve(cryptoService.decryptEntityId(request.getQuestionId()));*/

        /*if(question != null) {

            if(!question.getStatus().equals(QuestionStatus.ANSWERED)) {

                log.info("ex date :"+question.getExpirationDate());

                if(checkPulseExpiration(question.getExpirationDate(), ZonedDateTime.now())) {

                    if(request.getAnswers()!= null && request.getAnswers().size() != 0 ) {

                        request.getAnswers().forEach(answer -> {
                            if(answer.getId() != null || !answer.getFreeText().isEmpty()) {
                                QuestionAnswer newAnswer = new QuestionAnswer();
                                if(answer.getId() != null)
                                    newAnswer.setAnswerId(cryptoService.decryptEntityId(answer.getId()));
                                newAnswer.setFreeText(answer.getFreeText());
                                newAnswer.setQuestion(question);
                                answers.add(newAnswer);
                            }

                        });
                    }

                    List<QuestionAnswer> savedAnswers = answerService.create(answers,question);
                    responses = modelMapper.map(savedAnswers, AnswerSubmitResponse.class);

                } else {
                    throw new ComplexValidationException("answer", "platformAnswerSubmitRequest.answer.alreadyExpired");
                }

            } else {
                throw new ComplexValidationException("answer", "platformAnswerSubmitRequest.answer.alreadyAnswered");
            }
        } else {
            throw new ComplexValidationException("answer", "platformAnswerSubmitRequest.answer.wrong.questionId");
        }*/
        return new ResponseEntity<>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
    }

    public boolean checkPulseExpiration(ZonedDateTime expirationDate,ZonedDateTime now) {
        boolean notExpired = now.isBefore(expirationDate);
        return notExpired;
    }


}

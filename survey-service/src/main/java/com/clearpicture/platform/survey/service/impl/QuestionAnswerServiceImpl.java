package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.survey.entity.Question;
import com.clearpicture.platform.survey.entity.QuestionAnswer;
import com.clearpicture.platform.survey.enums.QuestionStatus;
import com.clearpicture.platform.survey.repository.QuestionAnswerRepository;
import com.clearpicture.platform.survey.repository.QuestionRepository;
import com.clearpicture.platform.survey.service.QuestionAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * AnswerServiceImpl
 * Created by nuwan on 9/7/18.
 */
@Transactional
@Slf4j
@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;


    @Override
    public List<QuestionAnswer> create(Set<QuestionAnswer> answers, Question question) {
        log.info("saving newAnswer :" + answers);
        List<QuestionAnswer> questionAnswers = new ArrayList<>();
        if(question != null) {
            question.setStatus(QuestionStatus.ANSWERED);
            questionRepository.save(question);
            questionAnswers = questionAnswerRepository.saveAll(answers);
        }
        return questionAnswers;
    }
}

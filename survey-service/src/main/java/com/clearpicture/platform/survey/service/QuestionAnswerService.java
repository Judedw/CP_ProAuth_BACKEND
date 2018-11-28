package com.clearpicture.platform.survey.service;

import com.clearpicture.platform.survey.entity.Question;
import com.clearpicture.platform.survey.entity.QuestionAnswer;

import java.util.List;
import java.util.Set;

/**
 * AnswerService
 * Created by nuwan on 9/7/18.
 */
public interface QuestionAnswerService {
    
    List<QuestionAnswer> create(Set<QuestionAnswer> answers, Question question);

    boolean checkQuestionAnswerStatus(Long id,Long answerId,String s);

    boolean checkQuestionAnswerStatusWithFreeText(Long questionId, String answer, String s);
}

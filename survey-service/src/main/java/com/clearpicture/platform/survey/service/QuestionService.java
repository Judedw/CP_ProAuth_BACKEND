package com.clearpicture.platform.survey.service;

import com.clearpicture.platform.survey.entity.Question;
import com.clearpicture.platform.survey.entity.criteria.QuestionCriteria;
import org.springframework.data.domain.Page;

/**
 * QuestionService
 * Created by nuwan on 8/17/18.
 */
public interface QuestionService {
    Question create(Question newQuestion);

    Page<Question> search(QuestionCriteria criteria);

    Question retrieve(Long questionId);

    Question update(Question question);

    Question delete(Long questionId);
}

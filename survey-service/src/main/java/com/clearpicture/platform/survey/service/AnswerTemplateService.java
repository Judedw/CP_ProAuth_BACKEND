package com.clearpicture.platform.survey.service;

import com.clearpicture.platform.survey.entity.AnswerTemplate;
import com.clearpicture.platform.survey.entity.criteria.AnswerTemplateCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * AnswerTemplateService
 * Created by nuwan on 8/18/18.
 */
public interface AnswerTemplateService {
    AnswerTemplate create(AnswerTemplate newAnswerTemplate);

    AnswerTemplate retrieve(Long id);

    List<AnswerTemplate> retrieveForSuggestions(String keyword);

    Page<AnswerTemplate> search(AnswerTemplateCriteria criteria);

    AnswerTemplate update(AnswerTemplate answerTemplate);

    AnswerTemplate delete(Long answerTemplateId);
}

package com.clearpicture.platform.survey.service;

import com.clearpicture.platform.survey.entity.Survey;
import com.clearpicture.platform.survey.entity.criteria.SurveySearchCriteria;
import org.springframework.data.domain.Page;

/**
 * SurveyService
 * Created by nuwan on 8/18/18.
 */
public interface SurveyService {
    Survey save(Survey survey);

    Page<Survey> search(SurveySearchCriteria criteria);

    Survey retrieve(Long surveyId);

    Survey update(Survey client);

    Survey delete(Long surveyId);
}

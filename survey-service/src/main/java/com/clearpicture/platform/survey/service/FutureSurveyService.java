package com.clearpicture.platform.survey.service;

import com.clearpicture.platform.survey.entity.FutureSurvey;
import com.clearpicture.platform.survey.entity.criteria.FutureSurveySearchCriteria;
import org.springframework.data.domain.Page;

/**
 * created by Raveen -  18/10/24
 * FutureSurveyController - Handling every advanced surveys & e votes
 */

public interface FutureSurveyService {
    FutureSurvey save(FutureSurvey futureSurvey);

    Page<FutureSurvey> search(FutureSurveySearchCriteria criteria);

    FutureSurvey update(FutureSurvey futureSurvey);

    FutureSurvey delete(Long id);
}

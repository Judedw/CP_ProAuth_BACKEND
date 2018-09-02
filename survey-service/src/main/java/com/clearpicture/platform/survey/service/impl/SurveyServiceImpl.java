package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.survey.entity.QSurvey;
import com.clearpicture.platform.survey.entity.Survey;
import com.clearpicture.platform.survey.entity.criteria.SurveySearchCriteria;
import com.clearpicture.platform.survey.repository.SurveyRepository;
import com.clearpicture.platform.survey.service.SurveyService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SurveyServiceImpl
 * Created by nuwan on 8/18/18.
 */
@Transactional
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Override
    public Survey save(Survey survey) {

        return surveyRepository.save(survey);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<Survey> search(SurveySearchCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.Direction.fromString(criteria.getSortDirection() == null ? null : criteria.getSortDirection()), criteria.getSortProperty());
        Page<Survey> surveys = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotBlank(criteria.getTopic())) {
            booleanBuilder.and(QSurvey.survey.topic.containsIgnoreCase(criteria.getTopic()));

        }

        if (booleanBuilder.hasValue()) {
            surveys = surveyRepository.findAll(booleanBuilder, page);
        } else {
            surveys = surveyRepository.findAll(page);
        }

        return surveys;

    }

    @Transactional(readOnly = true)
    @Override
    public Survey retrieve(Long surveyId) {
        return surveyRepository.getOne(surveyId);
    }

    @Override
    public Survey update(Survey survey) {

        Survey currentSurvey = surveyRepository.getOne(survey.getId());

        if(currentSurvey == null) {

        }
        currentSurvey.setTopic(survey.getTopic());
        currentSurvey.setType(survey.getType());
        currentSurvey.setStartDate(survey.getStartDate());
        currentSurvey.setEndDate(survey.getEndDate());

        return surveyRepository.save(currentSurvey);

    }

    @Override
    public Survey delete(Long surveyId) {
        Survey currentSurvey = surveyRepository.getOne(surveyId);
        surveyRepository.delete(currentSurvey);

        return currentSurvey;
    }
}

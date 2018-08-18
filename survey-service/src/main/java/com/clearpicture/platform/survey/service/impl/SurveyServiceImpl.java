package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.survey.entity.Survey;
import com.clearpicture.platform.survey.repository.SurveyRepository;
import com.clearpicture.platform.survey.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SurveyServiceImpl
 * Created by nuwan on 8/18/18.
 */
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Override
    public Survey save(Survey survey) {

        return surveyRepository.save(survey);

    }
}

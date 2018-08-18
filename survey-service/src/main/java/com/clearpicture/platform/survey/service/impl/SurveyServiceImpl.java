package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.survey.entity.Survey;
import com.clearpicture.platform.survey.service.SurveyService;
import org.springframework.stereotype.Service;

/**
 * SurveyServiceImpl
 * Created by nuwan on 8/18/18.
 */
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

    @Override
    public Survey save(Survey survey) {

        Survey newSurvey = new Survey();
        newSurvey.setName(survey.getName());

        return newSurvey;

    }
}

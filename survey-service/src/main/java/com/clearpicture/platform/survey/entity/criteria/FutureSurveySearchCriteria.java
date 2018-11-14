package com.clearpicture.platform.survey.entity.criteria;

import com.clearpicture.platform.survey.entity.FutureSurvey;
import lombok.Data;

/**
 * FutureSurveySearchRequest
 * Created by Raveen on 11/06/18.
 */
@Data
public class FutureSurveySearchCriteria extends FutureSurvey {

    private String name;

    private Integer pageNumber;

    private Integer pageSize;

    private String sortProperty;

    private String sortDirection;
}

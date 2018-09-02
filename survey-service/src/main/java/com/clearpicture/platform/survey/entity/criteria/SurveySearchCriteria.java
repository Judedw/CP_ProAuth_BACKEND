package com.clearpicture.platform.survey.entity.criteria;

import com.clearpicture.platform.survey.entity.Survey;
import lombok.Data;

/**
 * SurveySearchCriteria
 * Created by nuwan on 9/2/18.
 */
@Data
public class SurveySearchCriteria extends Survey {

    private String topic;

    private Integer pageNumber;

    private Integer pageSize;

    private String sortProperty;

    private String sortDirection;
}

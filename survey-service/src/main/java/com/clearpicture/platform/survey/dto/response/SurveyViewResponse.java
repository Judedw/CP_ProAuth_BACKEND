package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

/**
 * SurveyViewResponse
 * Created by nuwan on 9/2/18.
 */
@Data
public class SurveyViewResponse {

    private String id;

    private String topic;

    private String type;

    private String startDate;

    private String endDate;
}

package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

/**
 * SurveyUpdateResponse
 * Created by nuwan on 9/2/18.
 */
@Data
public class SurveyUpdateResponse {

    private String id;

    private String topic;

    private String startDate;

    private String endDate;
}

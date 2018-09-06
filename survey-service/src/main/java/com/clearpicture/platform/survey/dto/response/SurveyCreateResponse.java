package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

/**
 * SurveyCreateResponse
 * Created by nuwan on 8/17/18.
 */
@Data
public class SurveyCreateResponse {

    private String id;

    private String topic;

    private String type;

    private String startDate;

    private String endDate;

    private String voteId;

    private String productId;
}

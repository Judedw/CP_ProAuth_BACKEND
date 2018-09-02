package com.clearpicture.platform.survey.dto.response;

import com.clearpicture.platform.survey.enums.SurveyType;
import lombok.Data;

import java.time.LocalDate;

/**
 * SurveySearchResponse
 * Created by nuwan on 9/2/18.
 */
@Data
public class SurveySearchResponse {

    private String id;

    private String topic;

    private SurveyType type;

    private LocalDate startDate;

    private LocalDate endDate;

    private String productId;

    private String eVoteId;

}

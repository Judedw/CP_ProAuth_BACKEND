package com.clearpicture.platform.survey.dto.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * created by Raveen -  18/11/15
 * FutureSurveyAnswerRequest - Handling every advanced surveys & e votes
 */

@Data
public class FutureSurveyAnswerRequest {

    @NotBlank(message = "answer value is empty!")
    private String value;

    @NotBlank(message = "question code is empty!")
    private String qcode;

    private String ipAddress;

}



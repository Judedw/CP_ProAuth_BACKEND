package com.clearpicture.platform.survey.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * SurveyCreateRequest
 * Created by nuwan on 8/17/18.
 */
@Data
public class SurveyCreateRequest {

    @NotBlank(message = "surveyCreateRequest.name.empty")
    @Length(max = 100, message = "surveyCreateRequest.name.lengthExceeds")
    private String name;
}

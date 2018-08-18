package com.clearpicture.platform.survey.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnswerTemplateSearchResponse {

    private String id;

    private String name;

    private String answerTemplateType;
    
    private Boolean reverseDisplay;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lastModifiedDate;

}


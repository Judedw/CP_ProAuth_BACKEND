package com.clearpicture.platform.survey.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AnswerTemplateSearchResponse {

    private String id;

    private String name;

    private String answerTemplateType;
    
    private Boolean reverseDisplay;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime lastModifiedDate;

    private List<Answers> answers;


    @Data
    public static class Answers {

        private String id;

        private String lable;

        private BigDecimal value;

        private Integer optionNumber;

    }

}


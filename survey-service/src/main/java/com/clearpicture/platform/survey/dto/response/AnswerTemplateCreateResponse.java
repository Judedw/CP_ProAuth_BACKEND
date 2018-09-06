package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * AnswerTemplateCreateResponse
 * Created by nuwan on 8/18/18.
 */
@Data
public class AnswerTemplateCreateResponse {

    private String id;

    private String name;

    private String answerTemplateType;

    private List<Answers> answers;

    private Boolean reverseDisplay;

    @Data
    public static class Answers  {

        private String lable;

        private BigDecimal value;

        //To get the weight of the number(ex: good=5,best=10,better=15)
        private Integer optionNumber;

    }
}

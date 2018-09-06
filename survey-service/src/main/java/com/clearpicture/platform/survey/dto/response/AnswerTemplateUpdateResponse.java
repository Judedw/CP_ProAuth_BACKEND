package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

/**
 * 
 * @author Pasindu
 *
 */
@Data
public class AnswerTemplateUpdateResponse {

	private String id;

    private String name;

    private String answerTemplateType;

    //private List<Answers> answers;

    private Boolean reverseDisplay;

}

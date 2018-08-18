package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author Pasindu
 *
 */
@Data
public class AnswerTeamplateViewResponse {

	private String id;

	private String name;

	private String answerTemplateType;
	
	private Boolean reverseDisplay;

	private List<Answers> answers;
	

	@Data
	public static class Answers {

		private String id;

		private String lable;

		private BigDecimal value;

		private Integer optionNumber;

	}
}

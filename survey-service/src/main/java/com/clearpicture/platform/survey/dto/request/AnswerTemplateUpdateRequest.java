package com.clearpicture.platform.survey.dto.request;

import com.clearpicture.platform.modelmapper.ModelMappingAware;
import com.clearpicture.platform.survey.entity.Answer;
import com.clearpicture.platform.survey.enums.AnswerTemplateType;
import com.clearpicture.platform.survey.validation.annotation.ValidAnswerTemplateType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author Pasindu
 *
 */
@Data
public class AnswerTemplateUpdateRequest {

	@NotBlank(message = "answerTemplateUpdateRequest.name.empty")
	@Length(max = 100, message = "answerTemplateUpdateRequest.name.lengthExceeds")
	private String name;

	@ValidAnswerTemplateType(value = { AnswerTemplateType.FREE_TEXT, AnswerTemplateType.MULTIPLE_OPTIONS,
			AnswerTemplateType.SINGLE_ANSWER_OPTION }, ignoreOnNull = true, message = "answerTemplateCreateRequest.answerTemplateType.invalid")
	private String answerTemplateType;

	@Valid
	@NotEmpty(message = "answerTemplateUpdateRequest.answers.empty")
	private List<AnswerData> answers;
	
	private Boolean reverseDisplay;

	@Data
	public static class AnswerData implements ModelMappingAware {
		private String id;

		@NotEmpty
		@NotBlank
		@Length(max = 100, message = "answerTemplateCreateRequest.lable.lengthExceeds")
		private String lable;

		@Min(value = 0, message = "{answerTemplateUpdateRequest.answer.minValue.invalid}")
		@Max(value = 100, message = "{answerTemplateUpdateRequest.answer.maxValue.invalid}")
		private BigDecimal value;
 
		@NotNull
		private Integer optionNumber;

		@Override
		public Class<?> getDestinationType() {
			return Answer.class;
		}
	}

}

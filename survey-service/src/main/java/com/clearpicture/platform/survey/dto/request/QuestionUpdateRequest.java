package com.clearpicture.platform.survey.dto.request;

import com.clearpicture.platform.modelmapper.ModelMappingAware;
import com.clearpicture.platform.survey.entity.AnswerTemplate;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * @author nuwan
 *
 */
@Data
public class QuestionUpdateRequest {

	//@NotBlank(message = "questionnaireUpdateRequest.name.empty")
	@Length(max = 1000, message = "questionnaireUpdateRequest.name.lengthExceeds")
	private String name;

	//@Valid
	//@NotNull(message = "questionCreateRequest.answerTemplate.empty")
	private AnswerTemplateData answerTemplate;
	
	@Data
	public static class AnswerTemplateData implements ModelMappingAware {
		
		//@NotBlank(message = "questionCreateRequest.answerTemplate.id.empty")
		private String id;
		
		private String name;

		/* (non-Javadoc)
		 @see com.sodexo.begreat.questionnaireservice.modelmapper.ModelMappingAware#getDestinationType()*/
		 
		@Override
		public Class<?> getDestinationType() {
			return AnswerTemplate.class;
		}
	}

}

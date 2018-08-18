package com.clearpicture.platform.survey.dto.request;

import com.clearpicture.platform.dto.request.BaseSearchRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * @author nuwan
 *
 */
@Data
public class QuestionSearchRequest extends BaseSearchRequest {

	@Length(max = 1000, message = "questionnaireSearchRequest.keyword.lengthExceeds")
	private String keyword;
	
	private String sortProperty = "lastModifiedDate";

	@Length(max = 100, message = "questionnaireSearchRequest.answerTemplateId.lengthExceeds")
	private String answerTemplateId;


	@Pattern(regexp = "lastModifiedDate|name|scale.name|answerTemplate.name", flags = Pattern.Flag.CASE_INSENSITIVE,
			message = "{questionnaireSearchRequest.sortProperty.invalid}")
	@Override
	public String getSortProperty() {
		
		if(sortProperty.equals("scale") ) {
			sortProperty = "scale.name";
		}
		if(sortProperty.equals("answerTemplate") ) {
			sortProperty = "answerTemplate.name";
		}
		
		return sortProperty;
	}
	
	


}

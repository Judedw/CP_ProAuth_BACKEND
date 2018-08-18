package com.clearpicture.platform.survey.dto.request;

import com.clearpicture.platform.dto.request.BaseSearchRequest;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

/**
 * 
 * @author Pasindu
 *
 */
@Data
public class AnswerTeamplateSearchRequest extends BaseSearchRequest {

	private String sortProperty = "lastModifiedDate";
	
	@Length(max = 100, message = "platformUserSearchRequest.keyword.lengthExceeds")
	private String keyword;

	@Pattern(regexp = "lastModifiedDate|name", flags = Pattern.Flag.CASE_INSENSITIVE, message = "{answerTemplateSearchRequest.sortProperty.invalid}")
	@Override
	public String getSortProperty() {
		return sortProperty;
	}

}

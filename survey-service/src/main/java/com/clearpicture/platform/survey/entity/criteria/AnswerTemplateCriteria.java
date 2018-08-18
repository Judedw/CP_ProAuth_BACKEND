package com.clearpicture.platform.survey.entity.criteria;

import com.clearpicture.platform.survey.entity.AnswerTemplate;
import lombok.Data;

/**
 * 
 * @author Pasindu
 *
 */
@Data
public class AnswerTemplateCriteria extends AnswerTemplate {
	
	private String keyword;

	private Integer pageNumber;

	private Integer pageSize;

	private String sortProperty;

	private String sortDirection;

}

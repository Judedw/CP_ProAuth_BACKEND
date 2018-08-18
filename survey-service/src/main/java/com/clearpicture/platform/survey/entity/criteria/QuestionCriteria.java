package com.clearpicture.platform.survey.entity.criteria;

import com.clearpicture.platform.survey.entity.Question;
import lombok.Data;


/**
 * @author nuwan
 *
 */
@Data
public class QuestionCriteria extends Question {

	private String keyword;
	
	private Integer pageNumber;

	private Integer pageSize;

	private String sortProperty;

	private String sortDirection;
	
}

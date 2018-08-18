package com.clearpicture.platform.survey.entity.converter;

import com.clearpicture.platform.survey.enums.QuestionStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


/**
 * @author nuwan
 *
 */
@Converter(autoApply = true)
public class QuestionStatusConverter implements AttributeConverter<QuestionStatus, String>{

	/* (non-Javadoc)
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
	 */
	@Override
	public String convertToDatabaseColumn(QuestionStatus attribute) {
		return attribute.getValue();
	}

	/* (non-Javadoc)
	 * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
	 */
	@Override
	public QuestionStatus convertToEntityAttribute(String dbData) {
		return QuestionStatus.getEnum(dbData);
	}

}

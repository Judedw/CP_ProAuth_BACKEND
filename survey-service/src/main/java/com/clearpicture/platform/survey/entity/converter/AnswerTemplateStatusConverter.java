package com.clearpicture.platform.survey.entity.converter;

import com.clearpicture.platform.survey.enums.AnswerTemplateStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


/**
 * 
 * @author Pasindu
 *
 */
@Converter(autoApply = true)
public class AnswerTemplateStatusConverter implements AttributeConverter<AnswerTemplateStatus, String> {

	@Override
	public String convertToDatabaseColumn(AnswerTemplateStatus attribute) {
		return attribute.getValue();
	}

	@Override
	public AnswerTemplateStatus convertToEntityAttribute(String dbData) {
		return AnswerTemplateStatus.getEnum(dbData);
	}

}

package com.clearpicture.platform.survey.entity.converter;

import com.clearpicture.platform.survey.enums.AnswerTemplateType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


/**
 * 
 * @author Pasindu
 *
 */
@Converter(autoApply = true)
public class AnswerTeamplateTypeConverter implements AttributeConverter<AnswerTemplateType, String> {

	@Override
	public String convertToDatabaseColumn(AnswerTemplateType attribute) {
		return attribute.getValue();
	}

	@Override
	public AnswerTemplateType convertToEntityAttribute(String dbData) {
		return AnswerTemplateType.getEnum(dbData);
	}

}

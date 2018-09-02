package com.clearpicture.platform.survey.entity.converter;

import com.clearpicture.platform.survey.enums.SurveyType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * SurveyTypeConverter
 * Created by nuwan on 8/22/18.
 */
@Converter(autoApply = true)
public class SurveyTypeConverter implements AttributeConverter<SurveyType, String> {

    /* (non-Javadoc)
	 * @see javax.persistence.AttributeConverter#convertToDatabaseColumn(java.lang.Object)
	 */
    @Override
    public String convertToDatabaseColumn(SurveyType attribute) {
        if(attribute == null)
            return null;

            return attribute.getValue();
    }

    /* (non-Javadoc)
     * @see javax.persistence.AttributeConverter#convertToEntityAttribute(java.lang.Object)
     */
    @Override
    public SurveyType convertToEntityAttribute(String dbData) {
        return SurveyType.getEnum(dbData);
    }
}

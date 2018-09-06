package com.clearpicture.platform.survey.modelmapper.converter;

import com.clearpicture.platform.survey.enums.AnswerTemplateType;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * StringToAnswerTemplateTypeConverter
 * Created by nuwan on 9/3/18.
 */
@Component
public class StringToAnswerTemplateTypeConverter implements Converter<String, AnswerTemplateType> {

    @Override
    public AnswerTemplateType convert(MappingContext<String, AnswerTemplateType> context) {
        return AnswerTemplateType.getEnum(context.getSource());
    }

}

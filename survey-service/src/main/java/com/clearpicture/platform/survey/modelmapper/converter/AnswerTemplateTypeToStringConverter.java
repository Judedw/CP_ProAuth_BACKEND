package com.clearpicture.platform.survey.modelmapper.converter;

import com.clearpicture.platform.survey.enums.AnswerTemplateType;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * AnswerTemplateTypeToStringConverter
 * Created by nuwan on 9/2/18.
 */
@Component
public class AnswerTemplateTypeToStringConverter implements Converter<AnswerTemplateType,String>{
    @Override
    public String convert(MappingContext<AnswerTemplateType, String> context) {
        if (context.getSource() == null) {
            return null;
        }
        return context.getSource().getValue();
    }
}

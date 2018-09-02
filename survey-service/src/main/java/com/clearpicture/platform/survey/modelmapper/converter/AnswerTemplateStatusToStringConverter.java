package com.clearpicture.platform.survey.modelmapper.converter;

import com.clearpicture.platform.survey.enums.AnswerTemplateStatus;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * AnswerTemplateStatusToStringConverter
 * Created by nuwan on 9/2/18.
 */
@Component
public class AnswerTemplateStatusToStringConverter implements Converter<AnswerTemplateStatus,String>{
    @Override
    public String convert(MappingContext<AnswerTemplateStatus, String> context) {
        if (context.getSource() == null) {
            return null;
        }
        return context.getSource().getValue();
    }
}

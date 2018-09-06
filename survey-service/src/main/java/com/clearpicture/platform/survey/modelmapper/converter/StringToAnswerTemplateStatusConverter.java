package com.clearpicture.platform.survey.modelmapper.converter;

import com.clearpicture.platform.survey.enums.AnswerTemplateStatus;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * StringToAnswerTemplateStatusConverter
 * Created by nuwan on 9/3/18.
 */
@Component
public class StringToAnswerTemplateStatusConverter implements Converter<String, AnswerTemplateStatus> {

    @Override
    public AnswerTemplateStatus convert(MappingContext<String, AnswerTemplateStatus> context) {
        return AnswerTemplateStatus.getEnum(context.getSource());
    }

}



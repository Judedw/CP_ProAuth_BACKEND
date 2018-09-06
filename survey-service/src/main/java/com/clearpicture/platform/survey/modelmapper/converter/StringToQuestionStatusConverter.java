package com.clearpicture.platform.survey.modelmapper.converter;

import com.clearpicture.platform.survey.enums.QuestionStatus;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * StringToQuestionStatusConverter
 * Created by nuwan on 9/3/18.
 */
@Component
public class StringToQuestionStatusConverter implements Converter<String,QuestionStatus>{
    @Override
    public QuestionStatus convert(MappingContext<String, QuestionStatus> context) {
        return QuestionStatus.getEnum(context.getSource());
    }
}
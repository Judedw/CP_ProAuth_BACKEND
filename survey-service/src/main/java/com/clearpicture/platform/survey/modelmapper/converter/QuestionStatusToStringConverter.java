package com.clearpicture.platform.survey.modelmapper.converter;

import com.clearpicture.platform.survey.enums.QuestionStatus;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * QuestionStatusToStringConverter
 * Created by nuwan on 9/2/18.
 */
@Component
public class QuestionStatusToStringConverter implements Converter<QuestionStatus,String>{
    @Override
    public String convert(MappingContext<QuestionStatus, String> context) {

        if (context.getSource() == null) {
            return null;
        }
        return context.getSource().getValue();

    }
}

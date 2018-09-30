package com.clearpicture.platform.modelmapper.converter;

import com.clearpicture.platform.enums.SurveyType;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * SurveyTypeToStringConverter
 * Created by nuwan on 9/2/18.
 */
@Component
public class SurveyTypeToStringConverter implements Converter<SurveyType,String>{
    @Override
    public String convert(MappingContext<SurveyType, String> context) {
        if (context.getSource() == null) {
            return null;
        }
        return context.getSource().getValue();

    }
}

package com.clearpicture.platform.modelmapper.converter;

import com.clearpicture.platform.enums.SurveyType;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

/**
 * StringToSurveyTypeConverter
 * Created by nuwan on 9/2/18.
 */
@Component
public class StringToSurveyTypeConverter implements Converter<String,SurveyType>{
    @Override
    public SurveyType convert(MappingContext<String, SurveyType> context) {
        return SurveyType.getEnum(context.getSource());
    }
}

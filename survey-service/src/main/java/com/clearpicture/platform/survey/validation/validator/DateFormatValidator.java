package com.clearpicture.platform.survey.validation.validator;


import com.clearpicture.platform.enums.DateTimePattern;
import com.clearpicture.platform.survey.validation.annotation.DateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * DateFormatValidator
 * Created by nuwan on 9/2/18.
 */
public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {

    private DateTimePattern pattern;

    private boolean ignoreOnNull;

    @Override
    public void initialize(DateFormat constraintAnnotation) {
        pattern = constraintAnnotation.pattern();
        ignoreOnNull = constraintAnnotation.ignoreOnNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern.getPattern())
                    .withResolverStyle(ResolverStyle.STRICT);

            try {
                if (pattern == DateTimePattern.STRICT_DATE) {
                    LocalDate.parse(value, formatter);
                    return true;
                } else if (pattern == DateTimePattern.STRICT_DATE_TIME) {
                    LocalDateTime.parse(value, formatter);
                    return true;
                } else if (pattern == DateTimePattern.STRICT_TIME) {
                    LocalTime.parse(value, formatter);
                    return true;
                } else {
                    throw new IllegalArgumentException("Date mode argument is invalid");
                }

            } catch (DateTimeParseException e) {
                return false;
            }
        } else if (ignoreOnNull) {
            return true;
        } else {
            return false;
        }
    }
}

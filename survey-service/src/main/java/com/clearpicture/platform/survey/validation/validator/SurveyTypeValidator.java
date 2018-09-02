package com.clearpicture.platform.survey.validation.validator;

import com.clearpicture.platform.survey.enums.SurveyType;
import com.clearpicture.platform.survey.validation.annotation.ValidSurveyType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * SurveyTypeValidator
 * Created by nuwan on 8/23/18.
 */
public class SurveyTypeValidator implements ConstraintValidator<ValidSurveyType, String> {

    private SurveyType[] types;

    private boolean ignoreOnNull;

    @Override
    public void initialize(ValidSurveyType constraintAnnotation) {
        this.types = constraintAnnotation.value();
        this.ignoreOnNull = constraintAnnotation.ignoreOnNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
        if (ignoreOnNull && value == null) {
            return true;
        } else {
            for (SurveyType status : types) {
                if (status.getValue().equalsIgnoreCase(value)) {
                    return true;
                }
            }
            return false;
        }
    }
}

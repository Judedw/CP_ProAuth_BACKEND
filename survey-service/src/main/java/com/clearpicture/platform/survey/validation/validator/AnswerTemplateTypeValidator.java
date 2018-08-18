package com.clearpicture.platform.survey.validation.validator;

import com.clearpicture.platform.survey.enums.AnswerTemplateType;
import com.clearpicture.platform.survey.validation.annotation.ValidAnswerTemplateType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * AnswerTemplateTypeValidator
 * Created by nuwan on 8/18/18.
 */
public class AnswerTemplateTypeValidator implements ConstraintValidator<ValidAnswerTemplateType, String> {

    private AnswerTemplateType[] statuses;

    private boolean ignoreOnNull;

    @Override
    public void initialize(ValidAnswerTemplateType constraintAnnotation) {
        this.statuses = constraintAnnotation.value();
        this.ignoreOnNull = constraintAnnotation.ignoreOnNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
        if (ignoreOnNull && value == null) {
            return true;
        } else {
            for (AnswerTemplateType status : statuses) {
                if (status.getValue().equalsIgnoreCase(value)) {
                    return true;
                }
            }
            return false;
        }
    }
}

package com.clearpicture.platform.validation.validator.annotation;


import com.clearpicture.platform.enums.SurveyType;
import com.clearpicture.platform.validation.validator.SurveyTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ValidSurveyType
 * Created by nuwan on 8/23/18.
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SurveyTypeValidator.class)
@Documented
public @interface ValidSurveyType {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean ignoreOnNull() default false;

    SurveyType[] value();

    @Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        SurveyType[] value();
    }
}

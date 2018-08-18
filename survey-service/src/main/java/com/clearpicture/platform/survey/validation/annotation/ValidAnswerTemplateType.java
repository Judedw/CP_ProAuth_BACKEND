package com.clearpicture.platform.survey.validation.annotation;

import com.clearpicture.platform.survey.enums.AnswerTemplateType;
import com.clearpicture.platform.survey.validation.validator.AnswerTemplateTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ValidAnswerTemplateType
 * Created by nuwan on 8/18/18.
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AnswerTemplateTypeValidator.class)
@Documented
public @interface ValidAnswerTemplateType {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean ignoreOnNull() default false;

    AnswerTemplateType[] value();

    @Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        AnswerTemplateType[] value();
    }
}

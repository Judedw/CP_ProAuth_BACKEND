package com.clearpicture.platform.dto.response.wrapper;

import com.clearpicture.platform.dto.validation.ValidationFailure;
import com.clearpicture.platform.enums.RestApiResponseStatus;
import com.clearpicture.platform.response.wrapper.BaseResponseWrapper;
import lombok.Data;

import java.util.Collections;
import java.util.List;


public class ValidationFailureResponseWrapper extends BaseResponseWrapper {

    private List<ValidationFailure> ValidationFailures;

    public ValidationFailureResponseWrapper(List<ValidationFailure> ValidationFailures) {
        super(RestApiResponseStatus.VALIDATION_FAILURE);
        this.ValidationFailures = ValidationFailures;
    }

    public ValidationFailureResponseWrapper(String field, String code) {
        super(RestApiResponseStatus.VALIDATION_FAILURE);
        ValidationFailure vf = new ValidationFailure(field, code);
        this.ValidationFailures = Collections.singletonList(vf);
    }

    public List<ValidationFailure> getValidationFailures() {
        return ValidationFailures;
    }

    public void setValidationFailures(List<ValidationFailure> validationFailures) {
        ValidationFailures = validationFailures;
    }
}

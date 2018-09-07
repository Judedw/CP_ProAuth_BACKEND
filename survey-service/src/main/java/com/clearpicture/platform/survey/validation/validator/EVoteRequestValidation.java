package com.clearpicture.platform.survey.validation.validator;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.survey.dto.request.EVoteCreateRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * EVoteRequestValidation
 * Created by nuwan on 9/7/18.
 */
public class EVoteRequestValidation implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public void validate(Object o, Errors errors) {



    }

    public void validate(EVoteCreateRequest request) {

        if(request.getClientId() == null) {
            throw new ComplexValidationException("client","EVoteRequest.client.empty");
        }

        if(request.getTopic() == null) {
            throw new ComplexValidationException("client","EVoteRequest.client.empty");
        }

    }
}

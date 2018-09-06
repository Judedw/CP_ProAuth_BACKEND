package com.clearpicture.platform.survey.validation.validator;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.survey.dto.request.EVoteCreateRequest;
import lombok.Data;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * EVoteCreateRequestValidation
 * Created by nuwan on 9/4/18.
 */
@Data
public class EVoteCreateRequestValidation implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public void validate(Object o, Errors errors) {



    }

    public void validate(EVoteCreateRequest request) {

        if(request.getClient() == null) {
            throw new ComplexValidationException("client","EVoteCreateRequest.client.empty");
        }

        if(request.getTopic() == null) {
            throw new ComplexValidationException("client","EVoteCreateRequest.client.empty");
        }

    }
}

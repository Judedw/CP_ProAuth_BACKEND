package com.clearpicture.platform.survey.validation.validator;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.survey.dto.request.EVoteUpdateRequest;

/**
 * EVoteUpdateRequestValidation
 * Created by nuwan on 9/7/18.
 */
public class EVoteUpdateRequestValidation extends EVoteRequestValidation {

    public void validate(EVoteUpdateRequest request) {

        if(request.getClientId() == null) {
            throw new ComplexValidationException("client","EVoteUpdateRequest.client.empty");
        }

        if(request.getTopic() == null) {
            throw new ComplexValidationException("client","EVoteUpdateRequest.client.empty");
        }

    }
}

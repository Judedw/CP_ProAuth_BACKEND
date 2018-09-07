package com.clearpicture.platform.survey.validation.validator;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.survey.dto.request.EVoteCreateRequest;
import lombok.Data;

/**
 * EVoteCreateRequestValidation
 * Created by nuwan on 9/4/18.
 */
@Data
public class EVoteCreateRequestValidation extends EVoteRequestValidation {

    public void validate(EVoteCreateRequest request) {

        if(request.getClientId() == null) {
            throw new ComplexValidationException("client","EVoteCreateRequest.client.empty");
        }

        if(request.getTopic() == null) {
            throw new ComplexValidationException("client","EVoteCreateRequest.client.empty");
        }

    }
}

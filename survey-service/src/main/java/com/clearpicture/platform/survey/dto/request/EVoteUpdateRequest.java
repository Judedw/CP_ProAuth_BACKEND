package com.clearpicture.platform.survey.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * EVoteUpdateRequest
 * Created by nuwan on 9/7/18.
 */
@Data
public class EVoteUpdateRequest {

    @NotBlank(message = "EVoteCreateRequest.code.empty")
    private String code;

    private String description;

    @NotBlank(message = "EVoteCreateRequest.quantity.empty")
    private String quantity;

    private LocalDate expireDate;

    private String batchNumber;

    @NotBlank(message = "EVoteCreateRequest.clientId.empty")
    private String clientId;

    @NotBlank(message = "EVoteCreateRequest.topic.empty")
    private String topic;

    private String imageName;

    private String imageObject;

    private String surveyId;
}

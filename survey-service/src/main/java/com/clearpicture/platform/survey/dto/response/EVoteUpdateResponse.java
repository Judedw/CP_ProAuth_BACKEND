package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * EVoteUpdateResponse
 * Created by nuwan on 9/7/18.
 */
@Data
public class EVoteUpdateResponse {

    private String id;

    private String topic;

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    private String imageName;

    private String imageObject;

    private String clientId;

    private String surveyId;
}

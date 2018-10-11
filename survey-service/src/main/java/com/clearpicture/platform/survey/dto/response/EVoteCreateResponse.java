package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * EVoteCreateResponse
 * Created by nuwan on 8/23/18.
 */
@Data
public class EVoteCreateResponse {

    private String id;

    private String topic;

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    private String imageName;

    //private String imageObject;
    private byte[] imageObject;

    private String clientId;

    private String surveyId;
}

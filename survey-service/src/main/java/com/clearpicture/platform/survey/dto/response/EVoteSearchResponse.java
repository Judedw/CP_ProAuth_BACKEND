package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * EVoteSearchResponse
 * Created by nuwan on 8/23/18.
 */
@Data
public class EVoteSearchResponse {

    private String id;

    private String topic;

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    private String imageName;
}

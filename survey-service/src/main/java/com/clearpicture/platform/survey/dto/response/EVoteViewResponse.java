package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * EVoteViewResponse
 * Created by nuwan on 8/23/18.
 */
@Data
public class EVoteViewResponse {

    private String id;

    private String name;

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    private String imageName;
}

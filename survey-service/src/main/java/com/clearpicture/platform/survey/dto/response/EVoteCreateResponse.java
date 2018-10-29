package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

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

    //private String imageName;
    //private String imageObject;
    //private byte[] imageObject;
    private Set<EvoteImageData> imageObjects;

    private String clientId;

    private String surveyId;

    @Data
    public static class EvoteImageData {
        private String id;
    }
}

package com.clearpicture.platform.survey.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

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

    private LocalDate expireDate;

    private Integer batchNumber;

    private Set<EvoteImageData> imageObjects;

    private String clientId;

    private String surveyId;

    @Data
    public static class EvoteImageData {
        private String id;
    }
}

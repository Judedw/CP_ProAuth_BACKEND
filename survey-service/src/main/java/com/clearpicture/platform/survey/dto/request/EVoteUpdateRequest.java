package com.clearpicture.platform.survey.dto.request;

import com.clearpicture.platform.modelmapper.ModelMappingAware;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

/**
 * EVoteUpdateRequest
 * Created by nuwan on 9/7/18.
 */
@Data
public class EVoteUpdateRequest {

    @NotBlank(message = "EVoteCreateRequest.code.empty")
    private String code;

    private String description;

    private LocalDate expireDate;

    private String batchNumber;

    @NotBlank(message = "EVoteCreateRequest.clientId.empty")
    private String clientId;

    @NotBlank(message = "EVoteCreateRequest.topic.empty")
    private String topic;

    private List<String> remainImagesID;

    private List<EvoteImageUpdateRequest> imageObjects;

    private String surveyId;

    @Data
    public static class EvoteImageUpdateRequest implements ModelMappingAware {

        private String imageName;

        private byte[] imageObject;

        @Override
        public Class<?> getDestinationType() {
            return EvoteImageUpdateRequest.class;
        }
    }

}

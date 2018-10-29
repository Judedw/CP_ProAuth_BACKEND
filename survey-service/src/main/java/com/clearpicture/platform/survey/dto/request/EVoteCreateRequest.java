package com.clearpicture.platform.survey.dto.request;

import com.clearpicture.platform.modelmapper.ModelMappingAware;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

/**
 * EVoteCreateRequest
 * Created by nuwan on 8/23/18.
 */
@Data
public class EVoteCreateRequest {

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

    //private String imageName;
    //private String imageObject;
    //private byte[]  imageObject;
    private List<EvoteImageRequest> imageObjects;

    private String surveyId;

    @Data
    public static class EvoteImageRequest implements ModelMappingAware {

        private String imageName;

        private byte[] imageObject;

        @Override
        public Class<?> getDestinationType() {
            return EvoteImageRequest.class;
        }
    }


}

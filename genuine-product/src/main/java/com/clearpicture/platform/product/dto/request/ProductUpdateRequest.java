package com.clearpicture.platform.product.dto.request;

import com.clearpicture.platform.modelmapper.ModelMappingAware;
import com.clearpicture.platform.product.entity.Client;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

/**
 * ProductUpdateRequest
 * Created by nuwan on 7/27/18.
 */
@Data
public class ProductUpdateRequest {

    @NotBlank(message = "platformProductCreateRequest.product.code.empty")
    private String code;

    private String description;

    @NotBlank(message = "platformProductCreateRequest.product.quantity.empty")
    private String quantity;

    private LocalDate expireDate;

    private String batchNumber;

    @Valid
    private ProductUpdateRequest.ClientData client;

    @NotBlank(message = "platformProductCreateRequest.product.name.empty")
    private String name;

    //private String imageName;
    //private byte[] imageObject;
    private List<ProductImageUpdateRequest> imageObjects;

    private String surveyId;

    private List<String> remainImagesID;

    @Data
    public static class ClientData implements ModelMappingAware {

        @NotBlank(message = "platformProductCreateRequest.product.id.empty")
        private String id;

        @Override
        public Class<?> getDestinationType() {
            return Client.class;
        }
    }

    @Data
    public static class ProductImageUpdateRequest implements ModelMappingAware {

        private String imageName;

        private byte[] imageObject;

        @Override
        public Class<?> getDestinationType() {
            return ProductImageUpdateRequest.class;
        }
    }
}

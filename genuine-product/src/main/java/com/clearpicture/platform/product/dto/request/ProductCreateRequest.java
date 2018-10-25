package com.clearpicture.platform.product.dto.request;

import com.clearpicture.platform.modelmapper.ModelMappingAware;
import com.clearpicture.platform.product.entity.Client;
import com.clearpicture.platform.product.entity.Product;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

/**
 * ProductRequest
 * Created by nuwan on 7/12/18.
 * (C) Copyright 2018 nuwan (http://www.efuturesworld.com/) and others.
 */
@Data
public class ProductCreateRequest {

    @NotBlank(message = "platformProductUpdateRequest.product.code.empty")
    private String code;

    private String description;

    @NotBlank(message = "platformProductUpdateRequest.product.quantity.empty")
    private String quantity;

    private LocalDate expireDate;

    private String batchNumber;

    @Valid
    private ClientData client;

    @NotBlank(message = "platformProductUpdateRequest.product.name.empty")
    private String name;

    private List<ProductImageRequest> imageObjects;

    private String surveyId;

    @Data
    public static class ClientData implements ModelMappingAware {

        @NotBlank(message = "platformProductUpdateRequest.product.id.empty")
        private String id;

        @Override
        public Class<?> getDestinationType() {
            return Client.class;
        }
    }

    @Data
    public static class ProductImageRequest implements ModelMappingAware {

        private String imageName;

        private byte[] imageObject;

        @Override
        public Class<?> getDestinationType() {
            return ProductImageRequest.class;
        }
    }
}

package com.clearpicture.platform.product.dto.request;

import com.clearpicture.platform.modelmapper.ModelMappingAware;
import com.clearpicture.platform.product.entity.Client;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * ProductRequest
 * Created by nuwan on 7/12/18.
 * (C) Copyright 2018 nuwan (http://www.efuturesworld.com/) and others.
 */
@Data
public class ProductCreateRequest {

    @NotBlank(message = "platformProductCreateRequest.product.code.empty")
    private String code;

    private String description;

    @NotBlank(message = "platformProductCreateRequest.product.quantity.empty")
    private String quantity;

    private LocalDate expireDate;

    private String batchNumber;

    @Valid
    private ClientData client;

    @NotBlank(message = "platformProductCreateRequest.product.name.empty")
    private String name;

    private String imageName;

    private String imageObject;



    @Data
    public static class ClientData implements ModelMappingAware {

        @NotBlank(message = "platformProductCreateRequest.product.id.empty")
        private String id;

        @Override
        public Class<?> getDestinationType() {
            return Client.class;
        }
    }
}

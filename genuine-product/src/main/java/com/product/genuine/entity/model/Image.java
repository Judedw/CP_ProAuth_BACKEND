package com.product.genuine.entity.model;

import lombok.Data;

/**
 * Image
 * Created by nuwan on 8/8/18.
 */
@Data
public class Image {

    private String name;

    private String data;

    public Image(String name, String imageBase64) {
        this.name = name;
        this.data = imageBase64;
    }
}

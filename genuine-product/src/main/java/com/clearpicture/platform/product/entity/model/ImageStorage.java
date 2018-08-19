package com.clearpicture.platform.product.entity.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * ImageStorage
 * Created by nuwan on 8/18/18.
 */
@Getter
@Setter
@Entity
@Table(name="\"com.sap.sample::IMAGE_MANAGEMENT.IMAGE_STORE\"")
public class ImageStorage {

    private static final long serialVersionUID = 4829386985899147977L;

    @Id
    @Column(name="IMAGE_ID")
    private String imageId = null;

    @Lob
    @Column(name = "IMAGE_BINARY", length=100000000)
    private byte[] imageBinary = null;

    @Column(name = "IMAGE_MIME_TYPE")
    private String imageMimeType = null;

}

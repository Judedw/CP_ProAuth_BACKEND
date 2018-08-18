package com.clearpicture.platform.product.entity;

/**
 * EncryptedKey
 * Created by nuwan on 8/4/18.
 */

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(catalog = "product_db", name = "user_key")
public class UserKey {

    public UserKey(){}
    public UserKey(String appId,String publicKey,String privateKey){
        this.appId = appId;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String appId;

    @Column(length = 4000)
    private String publicKey;

    @Column(length = 4000)
    private String privateKey;
}

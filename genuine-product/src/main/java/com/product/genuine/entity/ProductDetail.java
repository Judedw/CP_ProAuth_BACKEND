package com.product.genuine.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.UUID;

/**
 * ProductDetail
 * Created by nuwan on 7/27/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "product_db", name = "product_detail")
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uniqueProductCode;

    @Column(unique = true)
    private String authenticationCode;

    private String description;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @PrePersist
    public void doPrePersist() {
        if (authenticationCode == null) {
            authenticationCode = UUID.randomUUID().toString();
        }
    }

}

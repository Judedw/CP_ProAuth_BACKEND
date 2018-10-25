package com.clearpicture.platform.product.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Lob;
import java.time.LocalDate;
import java.util.Set;

/**
 * Product
 * Created by nuwan on 7/12/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "product_db", name = "product")
public class Product extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    //private String imageName;
    //private String imageObject;
    //@Lob
    //private byte[] imageObject;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<ProductImage> imageObjects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    private Long surveyId;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<ProductDetail> productDetails;

    /*@OneToMany(mappedBy = "product",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<EvoteQuestion> questions;*/

    /*@EmbeddedId
    private ProductIdentity productIdentity;*///https://www.callicoder.com/hibernate-spring-boot-jpa-composite-primary-key-example/

}

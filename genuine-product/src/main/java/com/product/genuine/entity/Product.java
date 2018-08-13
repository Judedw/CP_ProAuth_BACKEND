package com.product.genuine.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
public class Product extends  CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    private String imageName;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy = "product",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<ProductDetail> productDetails;

    @OneToMany(mappedBy = "product",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<EvoteQuestion> questions;

    /*@EmbeddedId
    private ProductIdentity productIdentity;*///https://www.callicoder.com/hibernate-spring-boot-jpa-composite-primary-key-example/

}

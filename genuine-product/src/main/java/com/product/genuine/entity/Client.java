package com.product.genuine.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Client
 * Created by nuwan on 7/21/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "product_db", name = "client")
public class Client extends  CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    private String name;

    private String description;

    @OneToMany(mappedBy = "client")
    private Set<Product> products;
}

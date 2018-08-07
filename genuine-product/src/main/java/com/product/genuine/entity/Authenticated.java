package com.product.genuine.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

/**
 * authenticated
 * Created by nuwan on 7/28/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "product_db", name = "authenticated")
public class Authenticated extends  CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String authenticationCode;

    @OneToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    private Integer numberOfAuthentication;

    @OneToMany(mappedBy = "authenticated")
    private Set<authenticatedCustomer> authenticatedCustomers;
}
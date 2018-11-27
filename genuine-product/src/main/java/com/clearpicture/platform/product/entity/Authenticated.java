package com.clearpicture.platform.product.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * authenticated
 * Created by nuwan on 7/28/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "product_db", name = "authenticated")
public class Authenticated extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String authenticationCode;

    @OneToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    private Integer numberOfAuthentication;

    @OneToMany(mappedBy = "authenticated",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<AuthenticatedCustomer> authenticatedCustomers;

    private Integer recordNumber;

    @PrePersist
    public void doPrePersist() {
        recordNumber = new Integer(1);
    }
}

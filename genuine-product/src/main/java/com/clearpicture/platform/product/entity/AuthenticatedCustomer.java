package com.clearpicture.platform.product.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * authenticated_customer
 * Created by nuwan on 7/28/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "product_db", name = "authenticated_customer")
public class AuthenticatedCustomer extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    private String email;

    private String mobileNumber;

    private String city;

    private String province;

    @ManyToOne
    @JoinColumn(name ="authenticated")
    private Authenticated authenticated;

    private Integer recordNumber;

    @PrePersist
    public void doPrePersist() {
            recordNumber = new Integer(1);
    }
}

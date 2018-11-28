package com.clearpicture.platform.product.entity.user_authenticate;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


/**
 * User
 * Created by Buddhi on 11/28/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "product_db", name = "user")
public class User extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String description;

    private String address_line1;

    private String address_line2;

    private String city;

    private String postal_code;

    private String country;

    private String state;

    @Lob
    private byte[] imageObject;

    private String status;


}

package com.clearpicture.platform.survey.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * authenticated
 * Created by nuwan on 7/28/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "authenticated")
public class Authenticated extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String authenticationCode;

    @OneToOne
    @JoinColumn(name = "evote_detail_id")
    private EVoteDetail eVoteDetail;

    private Integer numberOfAuthentication;

    //@OneToMany(mappedBy = "authenticated")
    //private Set<authenticatedCustomer> authenticatedCustomers;
}

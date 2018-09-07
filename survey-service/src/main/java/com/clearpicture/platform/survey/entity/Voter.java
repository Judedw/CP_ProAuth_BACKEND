package com.clearpicture.platform.survey.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * Voter
 * Created by nuwan on 9/6/18.
 * Voter details planed to upload through csv file.
 */
@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "voter")
public class Voter extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String identityNumber;

    private Integer batchNumber;

    private Long clientId;
}

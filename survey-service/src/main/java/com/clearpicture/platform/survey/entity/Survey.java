package com.clearpicture.platform.survey.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
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
 * Survey
 * Created by nuwan on 8/17/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "survey")
public class Survey extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "survey")
    private Set<Question> questions;

}

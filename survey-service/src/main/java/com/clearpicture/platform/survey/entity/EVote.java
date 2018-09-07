package com.clearpicture.platform.survey.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

/**
 * EVote
 * Created by nuwan on 8/23/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "e_vote")
public class EVote extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    private String imageName;

    private String imageObject;

    private Long clientId;

    private Long surveyId;

    @OneToMany(mappedBy = "eVote",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<EVoteDetail> eVoteDetails;
}

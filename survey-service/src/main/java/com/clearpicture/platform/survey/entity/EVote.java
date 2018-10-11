package com.clearpicture.platform.survey.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    //private String imageObject;
    @Lob
    private byte[]  imageObject;

    private Long clientId;

    private Long surveyId;

    @OneToMany(mappedBy = "eVote",cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    private Set<EVoteDetail> eVoteDetails;
}

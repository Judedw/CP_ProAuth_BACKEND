package com.clearpicture.platform.survey.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.UUID;

/**
 * EVoteDetails
 * Created by nuwan on 9/6/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "e_vote_detail")
public class EVoteDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uniqueVoteCode;

    @Column(unique = true)
    private String authenticationCode;

    private String description;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private EVote eVote;

    @PrePersist
    public void doPrePersist() {
        if (authenticationCode == null) {
            authenticationCode = UUID.randomUUID().toString();
        }
    }
}

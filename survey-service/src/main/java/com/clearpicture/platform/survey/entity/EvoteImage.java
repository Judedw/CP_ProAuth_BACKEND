package com.clearpicture.platform.survey.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * created by Raveen -  18/10/24
 * FutureSurveyController - Handling every advanced surveys & e votes
 */

@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "evote_image")
public class EvoteImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;

    @Lob
    private byte[] imageObject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="evote_id")
    EVote evote;

}

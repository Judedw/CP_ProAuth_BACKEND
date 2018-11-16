package com.clearpicture.platform.survey.entity;


import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * created by Raveen -  18/11/15
 * FutureSurveyAnswer - Handling every advanced surveys & e votes
 */

@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "future_survey_answer")
public class FutureSurveyAnswer extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private String qcode;
    private String ipAddress;

    @ManyToOne
    @JoinColumn(name = "element_id")
    private Element element;
}

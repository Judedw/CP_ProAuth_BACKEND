package com.clearpicture.platform.survey.entity;


import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * created by Raveen -  18/10/24
 * FutureSurvey - Handling every advanced surveys & e votes
 */

@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "element")
public class Element extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long elementId;

    private String qcode;

    private String type;

    private String name;

    //private List<Object> choices;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page page;
}

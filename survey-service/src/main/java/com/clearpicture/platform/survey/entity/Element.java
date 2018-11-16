package com.clearpicture.platform.survey.entity;


import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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
    private Long id;

    private String qcode;

    private String type;

    private String name;

    @OneToMany(mappedBy = "element", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Choice> choices;

    @OneToMany(mappedBy = "element", cascade = {CascadeType.MERGE,CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<FutureSurveyAnswer> answers;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page page;
}

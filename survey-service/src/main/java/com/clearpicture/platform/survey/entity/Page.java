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
@Table(catalog = "survey_db", name = "page_table")
public class Page extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "future_survey_id")
    private FutureSurvey futureSurvey;

    @OneToMany(mappedBy = "page",cascade = {CascadeType.ALL},orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Element> elements;


}

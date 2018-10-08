package com.clearpicture.platform.survey.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import com.clearpicture.platform.enums.SurveyType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.ZonedDateTime;
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

    private String topic;

    private SurveyType type;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long productId;

    private Long voteId;

    @OneToMany(mappedBy = "survey",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Question> questions;

}

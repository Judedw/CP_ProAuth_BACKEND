package com.clearpicture.platform.survey.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import com.clearpicture.platform.survey.enums.SurveyType;
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

    //@Column(unique = true)
    private String topic;

    private SurveyType type;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long productId;

    private Long voteId;

    @OneToMany(mappedBy = "survey",cascade = {CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private Set<Question> questions;

    /*@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "survey_question",
            joinColumns = { @JoinColumn(name = "survey_id") },
            inverseJoinColumns = { @JoinColumn(name = "question_id") }
    )
    Set<Question> questions = new HashSet<>();*/

}

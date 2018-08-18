package com.clearpicture.platform.survey.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import com.clearpicture.platform.survey.enums.AnswerTemplateStatus;
import com.clearpicture.platform.survey.enums.AnswerTemplateType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * AnswerTemplate
 * Created by nuwan on 8/18/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "answer_template")
public class AnswerTemplate extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy="answerTemplate",cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Answer> answers;

    @OneToMany(mappedBy="answerTemplate",cascade= {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Question> questions;

    private AnswerTemplateType answerTemplateType;

    private AnswerTemplateStatus status;

    private Boolean reverseDisplay;

}

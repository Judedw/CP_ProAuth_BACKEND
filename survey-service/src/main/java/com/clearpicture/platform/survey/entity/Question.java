package com.clearpicture.platform.survey.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import com.clearpicture.platform.survey.enums.QuestionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.ZonedDateTime;

/**
 * Question
 * Created by nuwan on 8/17/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "question")
public class Question  extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(unique = true)
    private String name;

    private LocalDate expirationDate;

    private LocalDate startDate;

    private QuestionStatus status;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    /*@ManyToMany(mappedBy = "questions")
    private Set<Survey> surveys = new HashSet<>();*/

    @ManyToOne
    @JoinColumn(name="answer_template_id")
    private AnswerTemplate answerTemplate;




}

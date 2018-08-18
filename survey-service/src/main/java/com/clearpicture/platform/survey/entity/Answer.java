package com.clearpicture.platform.survey.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Answer
 * Created by nuwan on 8/18/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "answer")
public class Answer extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String extId;

    @ManyToOne
    @JoinColumn(name="answer_template_id", nullable = false)
    private AnswerTemplate answerTemplate;

    private String lable;

    private BigDecimal value;

    private Integer optionNumber;
}

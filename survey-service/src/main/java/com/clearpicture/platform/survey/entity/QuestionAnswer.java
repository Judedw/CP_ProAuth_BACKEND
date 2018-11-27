package com.clearpicture.platform.survey.entity;

import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * QuestionAnswer
 * Created by nuwan on 9/7/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "question_answer")
public class QuestionAnswer extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;

    private Long answerId;

    private String freeText;

    private String authCode;

    private Integer recordNumber;

    @PrePersist
    public void doPrePersist() {
        recordNumber = new Integer(1);
    }
}

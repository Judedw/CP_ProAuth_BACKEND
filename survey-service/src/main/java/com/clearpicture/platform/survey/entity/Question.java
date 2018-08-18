package com.clearpicture.platform.survey.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Question
 * Created by nuwan on 8/17/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "question")
public class Question  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
}

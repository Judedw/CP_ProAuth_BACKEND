package com.clearpicture.platform.survey.entity;


import com.clearpicture.platform.entity.CreateModifyAwareBaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * created by Raveen -  18/11/14
 * Choice - Handling every advanced surveys & e votes
 */

@Getter
@Setter
@Entity
@Table(catalog = "survey_db", name = "choice_table")
public class Choice extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private String text;

    @Lob
    @Column(length = 16777215)
    private String imageLink;

    @ManyToOne
    @JoinColumn(name = "element_table_id")
    private Element elementObj;

}

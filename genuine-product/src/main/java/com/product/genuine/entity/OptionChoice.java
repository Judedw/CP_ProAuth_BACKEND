package com.product.genuine.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * OptionChoice
 * Created by nuwan on 7/28/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "product_db", name = "option_choice")
public class OptionChoice extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "option_group_id")
    private OptionGroup optionGroup;
}

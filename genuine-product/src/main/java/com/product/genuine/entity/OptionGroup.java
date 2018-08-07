package com.product.genuine.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * OptionGroup
 * Created by nuwan on 7/28/18.
 */
@Getter
@Setter
@Entity
@Table(catalog = "product_db", name = "option_group")
public class OptionGroup extends CreateModifyAwareBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "optionGroup")
    private Set<EvoteQuestion> questions;
}

package com.clearpicture.platform.survey.repository;

import com.clearpicture.platform.survey.entity.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ElementRepository extends JpaRepository<Element, Long>, QuerydslPredicateExecutor<Element> {

    Element findByQcode(String qcode);
}

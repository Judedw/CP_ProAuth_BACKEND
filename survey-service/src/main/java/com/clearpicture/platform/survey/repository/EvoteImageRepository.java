package com.clearpicture.platform.survey.repository;

import com.clearpicture.platform.survey.entity.EvoteImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EvoteImageRepository  extends JpaRepository<EvoteImage,Long>, QuerydslPredicateExecutor<EvoteImage> {

}

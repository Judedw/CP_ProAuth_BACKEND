package com.clearpicture.platform.survey.repository;

import com.clearpicture.platform.survey.entity.EVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * EVoteRepository
 * Created by nuwan on 8/23/18.
 */
@Repository
public interface EVoteRepository extends JpaRepository<EVote,Long>,QuerydslPredicateExecutor<EVote>{
}

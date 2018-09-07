package com.clearpicture.platform.survey.repository;

import com.clearpicture.platform.survey.entity.EVoteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * EVoteDetailsRepository
 * Created by nuwan on 9/6/18.
 */
@Repository
public interface EVoteDetailsRepository extends JpaRepository<EVoteDetail,Long>,QuerydslPredicateExecutor<EVoteDetail> {

    @Query("SELECT coalesce(max(ev.id)+1, 0) FROM EVoteDetail ev")
    Long getMaxId();
}

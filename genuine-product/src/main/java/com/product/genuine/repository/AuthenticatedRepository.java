package com.product.genuine.repository;

import com.product.genuine.entity.Authenticated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * AuthenticatedRepository
 * Created by nuwan on 8/7/18.
 */
@Repository
public interface AuthenticatedRepository extends JpaRepository<Authenticated,Long>,QuerydslPredicateExecutor<Authenticated> {
}

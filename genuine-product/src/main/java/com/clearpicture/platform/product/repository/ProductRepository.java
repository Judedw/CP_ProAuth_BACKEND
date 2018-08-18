package com.clearpicture.platform.product.repository;

import com.clearpicture.platform.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository
 * Created by nuwan on 7/12/18.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long>,QuerydslPredicateExecutor<Product> {

    @Query(value = "SELECT max(p.id)+1 FROM Product p")
    Long getNextId();

    @Query(value = "SELECT seq_name.nextval FROM dual", nativeQuery =true)
    Long getNextSeriesId();



}

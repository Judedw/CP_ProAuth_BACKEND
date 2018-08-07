package com.product.genuine.repository;

import com.product.genuine.entity.Product;
import com.product.genuine.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * ProductDetailRepository
 * Created by nuwan on 7/27/18.
 */
@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail,Long>,QuerydslPredicateExecutor<ProductDetail> {

    @Query("SELECT coalesce(max(pd.id)+1, 0) FROM ProductDetail pd")
    Long getMaxId();
}

package com.clearpicture.platform.product.repository;

import com.clearpicture.platform.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage,Long>, QuerydslPredicateExecutor<ProductImage> {

}



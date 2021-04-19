package com.commerce.dao;

import java.util.List;

import com.commerce.model.entity.ProductCategory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {

    List<ProductCategory> findAllByOrderByName();

}

package com.commerce.dao;

import java.util.List;
import java.util.Optional;

import com.commerce.model.entity.Product;
import com.commerce.model.entity.ProductCategory;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByUrl(String url);

    List<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    List<Product> findAllByProductCategory(ProductCategory productCategory, Pageable pageable);

    List<Product> findAllByProductCategoryIsNot(ProductCategory productCategory, Pageable pageable);

    List<Product> findTop10ByOrderByCreatedAtDesc();    

    List<Product> findTop10ByProductCategoryAndIdIsNot(ProductCategory productCategory, Long id);    

}

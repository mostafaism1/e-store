package com.commerce.service.productCategory;

import java.util.List;

import com.commerce.model.response.category.ProductCategoryResponse;

public interface ProductCategoryService {
    List<ProductCategoryResponse> findAllByOrderByName();
}

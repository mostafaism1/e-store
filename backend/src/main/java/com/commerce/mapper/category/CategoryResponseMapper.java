package com.commerce.mapper.category;

import java.util.function.Function;

import com.commerce.model.dto.CategoryDTO;
import com.commerce.model.entity.ProductCategory;
import com.commerce.model.response.category.ProductCategoryResponse;

import org.springframework.stereotype.Component;

@Component
public class CategoryResponseMapper implements Function<ProductCategory, ProductCategoryResponse> {

    @Override
    public ProductCategoryResponse apply(ProductCategory productCategory) {

        if (productCategory == null) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO(productCategory.getName());

        ProductCategoryResponse productCategoryResponse = new ProductCategoryResponse();
        productCategoryResponse.setCategory(categoryDTO);

        return productCategoryResponse;

    }

}

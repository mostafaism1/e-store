package com.commerce.mapper.category;

import java.util.function.Function;

import com.commerce.model.dto.CategoryDTO;
import com.commerce.model.entity.ProductCategory;

import org.springframework.stereotype.Component;

@Component
public class CategoryDTOMapper implements Function<ProductCategory, CategoryDTO> {

    @Override
    public CategoryDTO apply(ProductCategory productCategory) {

        CategoryDTO categoryDTO = new CategoryDTO(productCategory.getName());

        return categoryDTO;

    }

}

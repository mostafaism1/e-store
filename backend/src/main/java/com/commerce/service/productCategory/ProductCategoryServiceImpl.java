package com.commerce.service.productCategory;

import java.util.List;
import java.util.stream.Collectors;

import com.commerce.dao.ProductCategoryRepository;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.category.CategoryResponseMapper;
import com.commerce.model.entity.ProductCategory;
import com.commerce.model.response.category.ProductCategoryResponse;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    public List<ProductCategoryResponse> findAllByOrderByName() {

        List<ProductCategory> productCategories = productCategoryRepository.findAllByOrderByName();

        if (productCategories.isEmpty()) {
            throw new ResourceNotFoundException("Could not find product categories");
        }

        return productCategories.stream().map(productCategory -> categoryResponseMapper.apply(productCategory))
                .collect(Collectors.toList());

    }

}

package com.commerce.api;

import java.util.List;

import com.commerce.model.response.category.ProductCategoryResponse;
import com.commerce.service.productCategory.ProductCategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/category")
@AllArgsConstructor
public class CategoryController extends PublicApiController {

    private final ProductCategoryService productCategoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategoryResponse>> getAllCategories() {

        List<ProductCategoryResponse> productCategories = productCategoryService.findAllByOrderByName();
        return new ResponseEntity<>(productCategories, HttpStatus.OK);

    }

}

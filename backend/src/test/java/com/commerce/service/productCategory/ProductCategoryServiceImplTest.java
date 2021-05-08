package com.commerce.service.productCategory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

import com.commerce.dao.ProductCategoryRepository;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.category.CategoryResponseMapper;
import com.commerce.model.dto.CategoryDTO;
import com.commerce.model.entity.ProductCategory;
import com.commerce.model.response.category.ProductCategoryResponse;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductCategoryServiceImplTest {

    @InjectMocks
    private ProductCategoryServiceImpl productCategoryServiceImpl;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Mock
    private CategoryResponseMapper categoryResponseMapper;

    private Faker faker;

    @BeforeEach
    void setup() {
        faker = new Faker();
    }

    @Test
    void it_should_find_all_categories() {

        // given
        String productCategoryName = faker.lorem().word();
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(productCategoryName);

        ProductCategoryResponse productCategoryResponse = new ProductCategoryResponse();
        productCategoryResponse.setCategory(CategoryDTO.builder().name(productCategoryName).build());

        given(productCategoryRepository.findAllByOrderByName()).willReturn(List.of(productCategory));
        given(categoryResponseMapper.apply(productCategory)).willReturn(productCategoryResponse);

        // when
        List<ProductCategoryResponse> productCategoryResponseList = productCategoryServiceImpl.findAllByOrderByName();

        // then
        then(productCategoryResponseList.size()).isEqualTo(1);
        then(productCategoryResponseList.get(0).getCategory().getName()).isEqualTo(productCategoryName);

    }

    @Test
    void it_should_throw_exception_when_no_category() {

        // given
        given(productCategoryRepository.findAllByOrderByName()).willReturn(Collections.emptyList());

        // when, then
        assertThatThrownBy(() -> productCategoryServiceImpl.findAllByOrderByName())
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Could not find product categories");

    }

}

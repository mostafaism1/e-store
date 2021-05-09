package com.commerce.service.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import com.commerce.dao.ProductRepository;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.product.ProductDetailsResponseMapper;
import com.commerce.model.entity.Product;
import com.commerce.model.response.product.ProductDetailsResponse;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDetailsResponseMapper productDetailsResponseMapper;

    private Faker faker;

    @BeforeEach
    void setup() {

        faker = new Faker();

    }

    @Test
    void it_should_find_product_by_url() {

        // given
        String url = faker.internet().domainSuffix();
        Product product = new Product();

        ProductDetailsResponse expected = new ProductDetailsResponse();

        given(productRepository.findByUrl(url)).willReturn(Optional.of(product));
        given(productDetailsResponseMapper.apply(product)).willReturn(expected);

        // when
        ProductDetailsResponse actual = productServiceImpl.findByUrl(url);

        // then
        then(actual).isEqualTo(expected);

    }

    @Test
    void it_should_throw_exception_when_no_product_found_by_url() {

        // given
        String url = faker.internet().domainSuffix();

        given(productRepository.findByUrl(url)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productServiceImpl.findByUrl(url)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with the url " + url);
    }

}

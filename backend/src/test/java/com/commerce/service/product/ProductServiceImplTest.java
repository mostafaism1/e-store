package com.commerce.service.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import com.commerce.dao.ProductRepository;
import com.commerce.dao.ProductVariantRepository;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.product.ProductDetailsResponseMapper;
import com.commerce.mapper.product.ProductVariantResponseMapper;
import com.commerce.model.entity.Product;
import com.commerce.model.entity.ProductVariant;
import com.commerce.model.response.product.ProductDetailsResponse;
import com.commerce.model.response.product.ProductVariantResponse;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDetailsResponseMapper productDetailsResponseMapper;

    @Mock
    private ProductVariantRepository productVariantRepository;

    @Mock
    private ProductVariantResponseMapper productVariantResponseMapper;

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

    @Test
    void it_should_get_all_product_variants() {

        // given
        Integer page = faker.number().randomDigitNotZero();
        Integer size = faker.number().randomDigitNotZero();
        String sort = faker.bool().bool() ? "lowest" : "highest";
        String category = faker.lorem().word();
        Double minPrice = (double) faker.number().randomNumber();
        Double maxPrice = minPrice + (double) faker.number().randomNumber();
        String color = faker.color().name();

        ProductVariant productVariant = new ProductVariant();
        List<ProductVariant> productVariants = List.of(productVariant);

        Page<ProductVariant> productVariantPage = new PageImpl<>(productVariants);

        ProductVariantResponse expected = new ProductVariantResponse();

        given(productVariantRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .willReturn(productVariantPage);
        given(productVariantResponseMapper.apply(any(ProductVariant.class))).willReturn(expected);

        // when
        List<ProductVariantResponse> actual = productServiceImpl.getAll(page, size, sort, category, minPrice, maxPrice,
                color);

        // then
        then(actual.size()).isEqualTo(1);
        then(actual.get(0)).isEqualTo(expected);
    }

    @Test
    void it_should_get_all_product_variants_with_no_sort() {

        // given
        Integer page = faker.number().randomDigitNotZero();
        Integer size = faker.number().randomDigitNotZero();
        String sort = null;
        String category = faker.lorem().word();
        Double minPrice = (double) faker.number().randomNumber();
        Double maxPrice = minPrice + faker.number().randomNumber();
        String color = faker.color().name();

        ProductVariant productVariant = new ProductVariant();
        List<ProductVariant> productVariants = List.of(productVariant);

        Page<ProductVariant> productVariantPage = new PageImpl<>(productVariants);

        ProductVariantResponse expected = new ProductVariantResponse();

        given(productVariantRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .willReturn(productVariantPage);
        given(productVariantResponseMapper.apply(any(ProductVariant.class))).willReturn(expected);

        // when
        List<ProductVariantResponse> actual = productServiceImpl.getAll(page, size, sort, category, minPrice, maxPrice,
                color);

        // then
        then(actual.size()).isEqualTo(1);
        then(actual.get(0)).isEqualTo(expected);
    }

    @Test
    void it_should_throw_exception_when_invalid_sort() {

        // given
        Integer page = faker.number().randomDigitNotZero();
        Integer size = faker.number().randomDigitNotZero();
        String sort = faker.random().hex();
        String category = faker.lorem().word();
        Double minPrice = (double) faker.number().randomNumber();
        Double maxPrice = minPrice + faker.number().randomNumber();
        String color = faker.color().name();

        // when, then
        assertThatThrownBy(() -> productServiceImpl.getAll(page, size, sort, category, minPrice, maxPrice, color))
                .isInstanceOf(InvalidArgumentException.class).hasMessage("Invalid sort parameter");
    }

}

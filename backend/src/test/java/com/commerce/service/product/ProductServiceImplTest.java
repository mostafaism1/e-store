package com.commerce.service.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.commerce.dao.ProductRepository;
import com.commerce.dao.ProductVariantRepository;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.product.ProductDetailsResponseMapper;
import com.commerce.mapper.product.ProductResponseMapper;
import com.commerce.mapper.product.ProductVariantResponseMapper;
import com.commerce.model.entity.Product;
import com.commerce.model.entity.ProductCategory;
import com.commerce.model.entity.ProductVariant;
import com.commerce.model.response.product.ProductDetailsResponse;
import com.commerce.model.response.product.ProductResponse;
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

    @Mock
    private ProductResponseMapper productResponseMapper;

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

    @Test
    void it_should_get_all_product_variant_count() {

        // given
        String category = faker.lorem().word();
        Double minPrice = (double) faker.number().randomNumber();
        Double maxPrice = minPrice + faker.number().randomNumber();
        String color = faker.color().name();

        Long expected = faker.number().randomNumber();

        given(productVariantRepository.count(any(Specification.class))).willReturn(expected);

        // when
        Long actual = productServiceImpl.getAllCount(category, minPrice, maxPrice, color);

        // then
        then(actual).isEqualTo(expected);

    }

    @Test
    void it_should_get_all_related_products() {

        // given
        String url = faker.internet().domainSuffix();

        Product product = new Product();
        product.setId(faker.number().randomNumber());
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(faker.lorem().word());
        product.setProductCategory(productCategory);

        List<Product> products = Stream.generate(Product::new).limit(faker.number().randomDigitNotZero())
                .collect(Collectors.toList());

        given(productRepository.findByUrl(url)).willReturn(Optional.of(product));
        given(productRepository.findTop10ByProductCategoryAndIdIsNot(product.getProductCategory(), product.getId()))
                .willReturn(products);

        // when
        List<ProductResponse> actual = productServiceImpl.getRelatedProducts(url);

        // then
        then(actual.size()).isEqualTo(products.size());

    }

    @Test
    void it_should_throw_exception_when_url_is_invalid_on_get_related_products() {

        // given
        String url = faker.internet().domainSuffix();

        given(productRepository.findByUrl(url)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> productServiceImpl.getRelatedProducts(url))
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Related products not found");

    }

    @Test
    void it_should_get_all_newly_added_products() {

        // given
        List<Product> products = Stream.generate(Product::new).limit(faker.number().randomDigitNotZero())
                .collect(Collectors.toList());

        given(productRepository.findTop10ByOrderByCreatedAtDesc()).willReturn(products);

        // when
        List<ProductResponse> actual = productServiceImpl.getNewlyAddedProducts();

        // then
        then(actual.size()).isEqualTo(products.size());

    }

    @Test
    void it_should_throw_exception_when_no_products_exist_on_get_newly_added_products() {

        // given
        given(productRepository.findTop10ByOrderByCreatedAtDesc()).willReturn(Collections.emptyList());

        // when, then
        assertThatThrownBy(() -> productServiceImpl.getNewlyAddedProducts())
                .isInstanceOf(ResourceNotFoundException.class).hasMessage("Newly added products not found");

    }

    @Test
    void it_should_get_all_most_selling_products() {

        // given
        List<ProductVariant> productVariants = Stream.generate(ProductVariant::new)
                .limit(faker.number().randomDigitNotZero()).collect(Collectors.toList());

        given(productVariantRepository.findTop10ByOrderBySellCountDesc()).willReturn(productVariants);

        // when
        List<ProductVariantResponse> productVariantResponseList = productServiceImpl.getMostSelling();

        // then
        then(productVariantResponseList.size()).isEqualTo(productVariants.size());

    }

    @Test
    void it_should_throw_exception_when_all_most_selling_products_not_found() {

        // given
        given(productVariantRepository.findTop10ByOrderBySellCountDesc()).willReturn(Collections.emptyList());

        // when, then
        assertThatThrownBy(() -> productServiceImpl.getMostSelling()).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Most selling products not found");

    }

    @Test
    void it_should_get_all_interested_products() {

        // given
        List<Product> products = Stream.generate(Product::new).limit(faker.number().randomDigitNotZero())
                .collect(Collectors.toList());

        given(productRepository.findTop10ByOrderByCreatedAtDesc()).willReturn(products);

        // when
        List<ProductResponse> productResponseList = productServiceImpl.getInterested();

        // then
        then(productResponseList.size()).isEqualTo(products.size());

    }

    @Test
    void it_should_throw_exception_when_all_interested_products_not_found() {

        // given
        given(productRepository.findTop10ByOrderByCreatedAtDesc()).willReturn(Collections.emptyList());

        // when, then
        assertThatThrownBy(() -> productServiceImpl.getInterested()).isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Interested products not found");

    }

}

package com.commerce.service.product;

import java.util.List;
import java.util.stream.Collectors;

import com.commerce.dao.ProductRepository;
import com.commerce.dao.ProductVariantRepository;
import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.product.ProductDetailsResponseMapper;
import com.commerce.mapper.product.ProductResponseMapper;
import com.commerce.mapper.product.ProductVariantResponseMapper;
import com.commerce.model.entity.Product;
import com.commerce.model.entity.ProductVariant;
import com.commerce.model.response.product.ProductDetailsResponse;
import com.commerce.model.response.product.ProductResponse;
import com.commerce.model.response.product.ProductVariantResponse;
import com.commerce.model.specs.ProductVariantSpecs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductDetailsResponseMapper productDetailsResponseMapper;
    private final ProductVariantResponseMapper productVariantResponseMapper;
    private final ProductResponseMapper productResponseMapper;

    @Override
    public ProductDetailsResponse findByUrl(String url) {

        Product product = productRepository.findByUrl(url)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with the url " + url));

        return productDetailsResponseMapper.apply(product);

    }

    @Override
    public List<ProductVariantResponse> getAll(Integer page, Integer size, String sort, String category,
            Double minPrice, Double maxPrice, String color) {

        PageRequest pageRequest;
        if (sort != null && !sort.isBlank()) {
            Sort sortRequest = getSort(sort);

            if (sortRequest == null) {
                throw new InvalidArgumentException("Invalid sort parameter");
            }
            pageRequest = PageRequest.of(page, size, sortRequest);
        } else {
            pageRequest = PageRequest.of(page, size);
        }

        Specification<ProductVariant> combinations = Specification.where(ProductVariantSpecs.withColor(color))
                .and(ProductVariantSpecs.withCategory(category)).and(ProductVariantSpecs.minPrice(minPrice))
                .and(ProductVariantSpecs.maxPrice(maxPrice));

        Page<ProductVariant> productVariants = productVariantRepository.findAll(combinations, pageRequest);

        return productVariants.stream().map(productVariantResponseMapper).collect(Collectors.toList());

    }

    @Override
    public Long getAllCount(String category, Double minPrice, Double maxPrice, String color) {

        Specification<ProductVariant> combinations = Specification.where(ProductVariantSpecs.withColor(color))
                .and(ProductVariantSpecs.withCategory(category)).and(ProductVariantSpecs.minPrice(minPrice))
                .and(ProductVariantSpecs.maxPrice(maxPrice));

        return productVariantRepository.count(combinations);

    }

    @Override
    public List<ProductResponse> getRelatedProducts(String url) {

        Product product = productRepository.findByUrl(url)
                .orElseThrow(() -> new ResourceNotFoundException("Related products not found"));
        List<Product> relatedProducts = productRepository
                .findTop10ByProductCategoryAndIdIsNot(product.getProductCategory(), product.getId());

        return relatedProducts.stream().map(productResponseMapper).collect(Collectors.toList());

    }

    @Override
    public List<ProductResponse> getNewlyAddedProducts() {

        List<Product> products = productRepository.findTop10ByOrderByCreatedAtDesc();
        if (products.size() == 0) {
            throw new ResourceNotFoundException("Newly added products not found");
        }

        return products.stream().map(productResponseMapper).collect(Collectors.toList());
    }

    @Override
    public List<ProductVariantResponse> getMostSelling() {

        List<ProductVariant> mostSellingProducts = productVariantRepository.findTop10ByOrderBySellCountDesc();
        if (mostSellingProducts.size() == 0) {
            throw new ResourceNotFoundException("Most selling products not found");
        }

        return mostSellingProducts.stream().map(productVariantResponseMapper).collect(Collectors.toList());

    }

    @Override
    public List<ProductResponse> getInterested() {
        
        List<Product> products = productRepository.findTop10ByOrderByCreatedAtDesc();
        if (products.size() == 0) {
            throw new ResourceNotFoundException("Interested products not found");
        }

        return products.stream().map(productResponseMapper).collect(Collectors.toList());

    }

    @Override
    public List<ProductResponse> searchProductDisplay(String keyword, Integer page, Integer size) {
        // TODO Auto-generated method stub
        return null;
    }

    private Sort getSort(String sort) {
        switch (sort) {
            case "lowest":
                return Sort.by(Sort.Direction.ASC, "price");
            case "highest":
                return Sort.by(Sort.Direction.DESC, "price");
            default:
                return null;
        }
    }

}

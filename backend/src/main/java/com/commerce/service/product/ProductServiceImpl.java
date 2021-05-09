package com.commerce.service.product;

import java.util.List;

import com.commerce.dao.ProductRepository;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.product.ProductDetailsResponseMapper;
import com.commerce.model.entity.Product;
import com.commerce.model.response.product.ProductDetailsResponse;
import com.commerce.model.response.product.ProductResponse;
import com.commerce.model.response.product.ProductVariantResponse;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDetailsResponseMapper productDetailsResponseMapper;

    @Override
    public ProductDetailsResponse findByUrl(String url) {

        Product product = productRepository.findByUrl(url)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with the url " + url));

        return productDetailsResponseMapper.apply(product);

    }

    @Override
    public List<ProductVariantResponse> getAll(Integer page, Integer size, String sort, String category,
            Double minPrice, Double maxPrice, String color) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long getAllCount(String category, Double minPrice, Double maxPrice, String color) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProductVariantResponse findProductVariantById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductResponse> getRelatedProducts(String url) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductResponse> getNewlyAddedProducts() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductVariantResponse> getMostSelling() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductResponse> getInterested() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductResponse> searchProductDisplay(String keyword, Integer page, Integer size) {
        // TODO Auto-generated method stub
        return null;
    }

}

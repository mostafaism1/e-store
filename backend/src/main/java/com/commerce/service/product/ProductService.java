package com.commerce.service.product;

import java.util.List;

import com.commerce.model.response.product.ProductDetailsResponse;
import com.commerce.model.response.product.ProductResponse;
import com.commerce.model.response.product.ProductVariantResponse;

public interface ProductService {

    ProductDetailsResponse findByUrl(String url);

    List<ProductVariantResponse> getAll(Integer page, Integer size, String sort, String category, Double minPrice,
            Double maxPrice, String color);

    Long getAllCount(String category, Double minPrice, Double maxPrice, String color);

    List<ProductResponse> getRelatedProducts(String url);

    List<ProductResponse> getNewlyAddedProducts();

    List<ProductVariantResponse> getMostSelling();

    List<ProductResponse> getInterested();

    List<ProductResponse> searchProductDisplay(String keyword, Integer page, Integer size);

}
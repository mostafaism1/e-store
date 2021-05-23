package com.commerce.api;

import java.util.List;
import java.util.Objects;

import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.model.response.product.ProductDetailsResponse;
import com.commerce.model.response.product.ProductResponse;
import com.commerce.model.response.product.ProductVariantResponse;
import com.commerce.service.product.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/product")
@AllArgsConstructor
public class ProductController extends PublicApiController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductVariantResponse>> getAll(@RequestParam("page") Integer page,
            @RequestParam("size") Integer pageSize, @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "color", required = false) String color) {

        if (page == null || page < 0) {
            throw new InvalidArgumentException("Invalid page");
        }
        if (pageSize == null || pageSize < 0) {
            throw new InvalidArgumentException("Invalid page size");
        }
        List<ProductVariantResponse> products = productService.getAll(page, pageSize, sort, category, minPrice,
                maxPrice, color);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(path = "/count")
    public ResponseEntity<Long> getAllCount(@RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "color", required = false) String color) {

        Long productCount = productService.getAllCount(category, minPrice, maxPrice, color);
        return new ResponseEntity<>(productCount, HttpStatus.OK);

    }

    @GetMapping(path = "/{url}")
    public ResponseEntity<ProductDetailsResponse> getByUrl(@PathVariable("url") String url) {

        if (url.isBlank()) {
            throw new InvalidArgumentException("Invalid url params");
        }
        ProductDetailsResponse productDetailsResponse = productService.findByUrl(url);
        return new ResponseEntity<>(productDetailsResponse, HttpStatus.OK);

    }

    @GetMapping(path = "/related/{url}")
    public ResponseEntity<List<ProductResponse>> getByRelated(@PathVariable("url") String url) {

        if (url.isBlank()) {
            throw new InvalidArgumentException("Invalid url params");
        }
        List<ProductResponse> products = productService.getRelatedProducts(url);
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @GetMapping(path = "/recent")
    public ResponseEntity<List<ProductResponse>> getByNewlyAdded() {

        List<ProductResponse> products = productService.getNewlyAddedProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @GetMapping(path = "/mostselling")
    public ResponseEntity<List<ProductVariantResponse>> getByMostSelling() {

        List<ProductVariantResponse> products = productService.getMostSelling();
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @GetMapping(path = "/interested")
    public ResponseEntity<List<ProductResponse>> getByInterested() {

        List<ProductResponse> products = productService.getInterested();
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam("page") Integer page,
            @RequestParam("size") Integer size, @RequestParam("keyword") String keyword) {

        List<ProductResponse> products = productService.searchProductDisplay(keyword, page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

}

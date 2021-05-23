package com.commerce.api;

import java.util.List;

import com.commerce.model.response.color.ProductColorResponse;
import com.commerce.service.color.ProductColorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/colors")
@AllArgsConstructor
public class ColorController extends PublicApiController {

    private final ProductColorService productColorService;

    @GetMapping
    public ResponseEntity<List<ProductColorResponse>> getAllColors() {

        List<ProductColorResponse> productColors = productColorService.findAll();
        return new ResponseEntity<>(productColors, HttpStatus.OK);

    }

}

package com.commerce.api;

import javax.validation.Valid;

import com.commerce.model.request.discount.ApplyDiscountRequest;
import com.commerce.service.discount.DiscountService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/cart/discount")
@AllArgsConstructor
public class DiscountController extends ApiController {

    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<HttpStatus> applyDiscount(@RequestBody @Valid ApplyDiscountRequest applyDiscountRequest) {
        discountService.applyDiscount(applyDiscountRequest.getCode());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

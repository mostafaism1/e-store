package com.commerce.api;

import java.util.List;

import javax.validation.Valid;

import com.commerce.error.exception.InvalidArgumentException;
import com.commerce.model.request.order.PostOrderRequest;
import com.commerce.model.response.order.OrderResponse;
import com.commerce.service.order.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/order")
@AllArgsConstructor
public class OrderController extends ApiController {

    private final OrderService orderService;

    @GetMapping(path = "/count")
    public ResponseEntity<Integer> getAllOrdersCount() {

        Integer orderCount = orderService.getAllOrdersCount();
        return new ResponseEntity<>(orderCount, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(@RequestParam("page") Integer page,
            @RequestParam("size") Integer pageSize) {

        if (page == null || page < 0) {
            throw new InvalidArgumentException("Invalid page");
        }
        if (pageSize == null || pageSize < 0) {
            throw new InvalidArgumentException("Invalid page size");
        }
        List<OrderResponse> orders = orderService.getAllOrders(page, pageSize);
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<OrderResponse> postOrder(@RequestBody @Valid PostOrderRequest postOrderRequest) {

        OrderResponse orderResponse = orderService.postOrder(postOrderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);

    }

}

package com.commerce.dao;

import java.util.List;
import java.util.Optional;

import com.commerce.model.entity.Order;
import com.commerce.model.entity.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserOrderByPlacedAtDesc(User user, Pageable pageable);

    Optional<Integer> countAllByUser(User user);

}

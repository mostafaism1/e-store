package com.commerce.dao;

import java.util.Optional;

import com.commerce.model.entity.Discount;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends CrudRepository<Discount, Long> {

    Optional<Discount> findByCode(String code);

}

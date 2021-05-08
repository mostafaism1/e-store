package com.commerce.service.color;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.commerce.dao.ColorRepository;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.color.ColorResponseMapper;
import com.commerce.model.entity.Color;
import com.commerce.model.response.color.ProductColorResponse;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductColorServiceImpl implements ProductColorService {

    private final ColorRepository colorRepository;
    private final ColorResponseMapper colorResponseMapper;

    @Override
    public List<ProductColorResponse> findAll() {

        Iterable<Color> colors = colorRepository.findAll();

        if (colors.spliterator().getExactSizeIfKnown() == 0) {
            throw new ResourceNotFoundException("Could not find product colors");
        }

        return StreamSupport.stream(colors.spliterator(), true).map(color -> colorResponseMapper.apply(color))
                .collect(Collectors.toList());

    }

}

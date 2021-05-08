package com.commerce.service.color;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

import com.commerce.dao.ColorRepository;
import com.commerce.error.exception.ResourceNotFoundException;
import com.commerce.mapper.color.ColorResponseMapper;
import com.commerce.model.dto.ColorDTO;
import com.commerce.model.entity.Color;
import com.commerce.model.response.color.ProductColorResponse;
import com.github.javafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductColorServiceImplTest {

    @InjectMocks
    ProductColorServiceImpl productColorServiceImpl;

    private Faker faker;

    @Mock
    private ColorRepository colorRepository;

    @Mock
    private ColorResponseMapper colorResponseMapper;

    @BeforeEach
    void setup() {

        faker = new Faker();

    }

    @Test
    void it_should_find_all_product_colors() {

        // given
        String colorName = faker.color().name();
        String colorHex = faker.color().hex();

        Color color = new Color();
        color.setName(colorName);
        color.setHex(colorHex);

        ProductColorResponse productColorResponse = new ProductColorResponse();
        productColorResponse.setColor(ColorDTO.builder().name(colorName).hex(colorHex).build());

        given(colorRepository.findAll()).willReturn(List.of(color));
        given(colorResponseMapper.apply(color)).willReturn(productColorResponse);

        // when
        List<ProductColorResponse> actual = productColorServiceImpl.findAll();

        // then
        then(actual.size()).isEqualTo(1);
        then(actual.get(0).getColor().getName()).isEqualTo(colorName);
        then(actual.get(0).getColor().getHex()).isEqualTo(colorHex);

    }

    @Test
    void it_should_throw_exception_when_no_color() {

        // given
        given(colorRepository.findAll()).willReturn(Collections.emptyList());

        // when, then
        assertThatThrownBy(() -> productColorServiceImpl.findAll()).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Could not find product colors");

    }

}

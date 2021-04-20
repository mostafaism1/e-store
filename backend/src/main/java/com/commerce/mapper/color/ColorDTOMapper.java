package com.commerce.mapper.color;

import java.util.function.Function;

import com.commerce.model.dto.ColorDTO;
import com.commerce.model.entity.Color;

import org.springframework.stereotype.Component;

@Component
public class ColorDTOMapper implements Function<Color, ColorDTO> {

    @Override
    public ColorDTO apply(Color color) {

        if (color == null) {
            return null;
        }

        return ColorDTO.builder().name(color.getName()).hex(color.getHex()).build();

    }

}

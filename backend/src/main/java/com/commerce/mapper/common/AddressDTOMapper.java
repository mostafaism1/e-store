package com.commerce.mapper.common;

import java.util.function.Function;

import com.commerce.model.common.Address;
import com.commerce.model.dto.AddressDTO;

import org.springframework.stereotype.Component;

@Component
public class AddressDTOMapper implements Function<Address, AddressDTO> {

    @Override
    public AddressDTO apply(Address address) {

        AddressDTO addressDTO = new AddressDTO(address.getCountry(), address.getState(), address.getCity(),
                address.getAddress(), address.getZip(), address.getPhone());

        return addressDTO;

    }

}

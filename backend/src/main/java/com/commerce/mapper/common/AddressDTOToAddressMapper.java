package com.commerce.mapper.common;

import java.util.function.Function;

import com.commerce.model.common.Address;
import com.commerce.model.dto.AddressDTO;

import org.springframework.stereotype.Component;

@Component
public class AddressDTOToAddressMapper implements Function<AddressDTO, Address> {

    @Override
    public Address apply(AddressDTO addressDTO) {

        if (addressDTO == null)
            return null;

        Address address = new Address();
        address.setCountry(addressDTO.getCountry());
        address.setState(addressDTO.getState());
        address.setCity(addressDTO.getCity());
        address.setAddress(addressDTO.getAddress());
        address.setZip(addressDTO.getZip());
        address.setPhone(addressDTO.getPhone());

        return address;
    }

}

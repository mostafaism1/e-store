package com.commerce.mapper.user;

import java.util.function.Function;

import com.commerce.mapper.common.AddressDTOMapper;
import com.commerce.model.entity.User;
import com.commerce.model.response.user.UserResponse;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserResponseMapper implements Function<User, UserResponse> {

    private AddressDTOMapper addressDTOMapper;

    @Override
    public UserResponse apply(User user) {

        if (user == null) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        userResponse.setEmail(user.getEmail());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setAddress(addressDTOMapper.apply(user.getAddress()));
        userResponse.setIsVerified(user.getIsVerified());

        return userResponse;

    }

}

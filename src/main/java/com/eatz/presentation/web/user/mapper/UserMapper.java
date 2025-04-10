package com.eatz.presentation.web.user.mapper;

import com.eatz.domain.address.Address;
import com.eatz.presentation.web.address.dto.AddressResponse;
import com.eatz.domain.user.User;
import com.eatz.presentation.web.user.dto.UserRequest;
import com.eatz.presentation.web.user.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserRequest dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        Address e = new Address();
        e.setStreet(dto.getAddress().getStreet());
        e.setCity(dto.getAddress().getCity());
        e.setState(dto.getAddress().getState());
        e.setZipCode(dto.getAddress().getZipCode());


        user.setAddress(e);
        return user;
    }

    public UserResponse toResponse(User user) {
        AddressResponse endereco = new AddressResponse(
                user.getAddress().getState(),
                user.getAddress().getCity(),
                user.getAddress().getState(),
                user.getAddress().getZipCode()
        );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                endereco
        );
    }
}

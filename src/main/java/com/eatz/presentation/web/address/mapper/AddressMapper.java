package com.eatz.presentation.web.address.mapper;

import com.eatz.domain.address.Address;
import com.eatz.presentation.web.address.dto.AddressRequest;
import com.eatz.presentation.web.address.dto.AddressResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AddressMapper {

    public Address toDomain(AddressRequest request) {
        Address address = new Address();
        address.setStreet(request.street());
        address.setCity(request.city());
        address.setState(request.state());
        address.setZipCode(request.zipCode());
        address.setCreatedAt(String.valueOf(LocalDateTime.now()));
        address.setUpdatedAt(String.valueOf(LocalDateTime.now()));
        address.setDeleted(false);
        return address;
    }

    public AddressResponse toResponse(Address address) {
        return new AddressResponse(
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }
}

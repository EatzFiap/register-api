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
        address.setStreet(request.getStreet());
        address.setNumber(request.getNumber());
        address.setComplement(request.getComplement());
        address.setNeighbourhood(request.getNeighbourhood());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setZipCode(request.getZipCode());
        address.setCreatedAt(String.valueOf(LocalDateTime.now()));
        address.setDeleted(false);
        return address;
    }

    public AddressResponse toResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighbourhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }
}

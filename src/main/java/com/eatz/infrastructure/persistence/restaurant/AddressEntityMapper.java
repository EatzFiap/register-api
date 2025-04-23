package com.eatz.infrastructure.persistence.restaurant;

import com.eatz.domain.address.Address;
import com.eatz.infrastructure.persistence.address.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressEntityMapper {

    public static Address toDomain(AddressEntity entity) {
        if (entity == null) return null;

        Address address = new Address();
        address.setId(entity.getId());
        address.setStreet(entity.getStreet());
        address.setNumber(entity.getNumber());
        address.setComplement(entity.getComplement());
        address.setCity(entity.getCity());
        address.setNeighbourhood(entity.getNeighbourhood());
        address.setState(entity.getState());
        address.setZipCode(entity.getZipCode());
        address.setCreatedAt(entity.getCreatedAt());
        address.setUpdatedAt(entity.getUpdatedAt());
        address.setDeleted(entity.isDeleted());
        return address;
    }

    public static AddressEntity toEntity(Address address) {
        if (address == null) return null;

        AddressEntity entity = new AddressEntity();
        entity.setId(address.getId());
        entity.setStreet(address.getStreet());
        entity.setNumber(address.getNumber());
        entity.setComplement(address.getComplement());
        entity.setCity(address.getCity());
        entity.setNeighbourhood(address.getNeighbourhood());
        entity.setState(address.getState());
        entity.setZipCode(address.getZipCode());
        entity.setCreatedAt(address.getCreatedAt());
        entity.setUpdatedAt(address.getUpdatedAt());
        entity.setDeleted(address.isDeleted());
        return entity;
    }
}
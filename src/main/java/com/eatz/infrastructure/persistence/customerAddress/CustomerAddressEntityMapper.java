package com.eatz.infrastructure.persistence.customerAddress;

import com.eatz.domain.customerAddresses.CustomerAddress;

public class CustomerAddressEntityMapper {

    private CustomerAddressEntityMapper() {
        // Impede instanciação
    }

    public static CustomerAddressEntity toEntity(CustomerAddress domain) {
        if (domain == null) return null;

        CustomerAddressEntity entity = new CustomerAddressEntity();
        entity.setIdCustomerAddress(domain.getId());
        entity.setCustomerId(domain.getCustomerId());
        entity.setAddressId(domain.getAddressId());
        entity.setNickname(domain.getNickname());
        entity.setDefault(domain.isDefault());
        entity.setDeleted(domain.isDeleted());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        return entity;
    }

    public static CustomerAddress toDomain(CustomerAddressEntity entity) {
        if (entity == null) return null;

        CustomerAddress domain = new CustomerAddress();
        domain.setId(entity.getIdCustomerAddress());
        domain.setCustomerId(entity.getCustomerId());
        domain.setAddressId(entity.getAddressId());
        domain.setNickname(entity.getNickname());
        domain.setDefault(entity.isDefault());
        domain.setDeleted(entity.isDeleted());

        return domain;
    }
}
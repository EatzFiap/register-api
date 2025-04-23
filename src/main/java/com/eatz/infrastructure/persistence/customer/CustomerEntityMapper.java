package com.eatz.infrastructure.persistence.customer;

import com.eatz.domain.customer.Customer;

import com.eatz.infrastructure.persistence.restaurant.AddressEntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerEntityMapper {

    public Customer toDomain(CustomerEntity entity) {
        return new Customer(
                entity.getIdCustomerUser(),
                entity.getName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getCpf(),
                entity.getPhone(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isDeleted(),
                entity.getProfileImageUrl(),
                entity.getAddresses() != null
                        ? entity.getAddresses().stream().map(AddressEntityMapper::toDomain).collect(Collectors.toList())
                        : null
        );
    }

    public CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setIdCustomerUser(customer.getIdCustomerUser());
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setPassword(customer.getPassword());
        entity.setCpf(customer.getCpf());
        entity.setPhone(customer.getPhone());
        entity.setCreatedAt(customer.getCreatedAt());
        entity.setUpdatedAt(customer.getUpdatedAt());
        entity.setDeleted(customer.isDeleted());
        entity.setProfileImageUrl(customer.getProfileImageUrl());
        entity.setAddresses(customer.getAddresses() != null
                ? customer.getAddresses().stream().map(AddressEntityMapper::toEntity).collect(Collectors.toList())
                : null
        );
        return entity;
    }
}

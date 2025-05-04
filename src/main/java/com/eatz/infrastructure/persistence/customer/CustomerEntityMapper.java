package com.eatz.infrastructure.persistence.customer;

import com.eatz.domain.customer.Customer;

import org.springframework.stereotype.Component;

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
                entity.getProfileImageUrl()
        );
    }

    public CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setIdCustomerUser(customer.getId());
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setPassword(customer.getPassword());
        entity.setCpf(customer.getCpf());
        entity.setPhone(customer.getPhone());
        entity.setCreatedAt(customer.getCreatedAt());
        entity.setUpdatedAt(customer.getUpdatedAt());
        entity.setDeleted(customer.isDeleted());
        entity.setProfileImageUrl(customer.getProfileImageUrl());
        return entity;
    }
}

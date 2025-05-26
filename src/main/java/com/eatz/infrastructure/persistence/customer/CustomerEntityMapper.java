package com.eatz.infrastructure.persistence.customer;

import com.eatz.domain.address.CustomerAddressDetails;
import com.eatz.domain.customer.Customer;

import com.eatz.infrastructure.persistence.address.AddressEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerEntityMapper {

    public Customer toDomain(CustomerEntity entity) {
        List<CustomerAddressDetails> addresses = entity.getCustomerAddresses().stream()
                .filter(customerAddress -> !customerAddress.isDeleted() && !customerAddress.getAddress().isDeleted())
                .map(customerAddress -> {
                    AddressEntity address = customerAddress.getAddress();
                    return new CustomerAddressDetails(
                            address.getId(),
                            address.getStreet(),
                            address.getNumber(),
                            address.getComplement(),
                            address.getCity(),
                            address.getNeighbourhood(),
                            address.getState(),
                            address.getZipCode(),
                            customerAddress.getNickname(),
                            customerAddress.isDefault()
                    );
                }).toList();

        return new Customer(entity, addresses);
    }

    public CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
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

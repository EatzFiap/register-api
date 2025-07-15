package com.eatz.presentation.web.customer.mapper;

import com.eatz.domain.address.Address;
import com.eatz.presentation.web.address.dto.AddressResponse;
import com.eatz.domain.customer.Customer;
import com.eatz.presentation.web.customer.dto.CustomerRequest;
import com.eatz.presentation.web.customer.dto.CustomerResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomerMapper {
    public Customer toDomain(CustomerRequest dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(dto.getPassword());
        customer.setPhone(dto.getPhone());

        Address address = new Address();
        address.setStreet(dto.getAddress().street());
        address.setCity(dto.getAddress().city());
        address.setState(dto.getAddress().state());
        address.setZipCode(dto.getAddress().zipCode());

        customer.setAddresses(Collections.singletonList(address));

        return customer;
    }

    public CustomerResponse toResponse(Customer customer) {
        Address address = customer.getAddresses() != null && !customer.getAddresses().isEmpty()
                ? customer.getAddresses().getFirst()
                : null;

        AddressResponse addressResponse = null;
        if (address != null) {

            addressResponse = new AddressResponse(
                    address.getStreet(),
                    address.getCity(),
                    address.getState(),
                    address.getZipCode()
            );
        }

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                addressResponse
        );
    }
}

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

        Address address = new Address();
        address.setStreet(dto.getAddress().getStreet());
        address.setCity(dto.getAddress().getCity());
        address.setState(dto.getAddress().getState());
        address.setZipCode(dto.getAddress().getZipCode());

        customer.setAddresses(Collections.singletonList(address));

        return customer;
    }

    public CustomerResponse toResponse(Customer customer) {
        Address address = customer.getAddresses() != null && !customer.getAddresses().isEmpty()
                ? customer.getAddresses().get(0)
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
                customer.getIdCustomerUser(),
                customer.getName(),
                customer.getEmail(),
                addressResponse
        );
    }
}

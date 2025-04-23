package com.eatz.presentation.web.customer.mapper;

import com.eatz.domain.address.Address;
import com.eatz.presentation.web.address.dto.AddressResponse;
import com.eatz.domain.customer.Customer;
import com.eatz.presentation.web.customer.dto.CustomerRequest;
import com.eatz.presentation.web.customer.dto.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toDomain(CustomerRequest dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(dto.getPassword());

        Address e = new Address();
        e.setStreet(dto.getAddress().getStreet());
        e.setCity(dto.getAddress().getCity());
        e.setState(dto.getAddress().getState());
        e.setZipCode(dto.getAddress().getZipCode());


        customer.setAddress(e);
        return customer;
    }

    public CustomerResponse toResponse(Customer customer) {
        AddressResponse endereco = new AddressResponse(
                customer.getAddress().getState(),
                customer.getAddress().getCity(),
                customer.getAddress().getState(),
                customer.getAddress().getZipCode()
        );

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                endereco
        );
    }
}

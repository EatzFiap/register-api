package com.eatz.presentation.web.customer.mapper;

import com.eatz.domain.address.Address;
import com.eatz.presentation.web.address.dto.AddressResponse;
import com.eatz.domain.customer.Customer;
import com.eatz.presentation.web.customer.dto.CustomerRequest;
import com.eatz.presentation.web.customer.dto.CustomerResponse;
import com.eatz.presentation.web.customer.dto.UpdateCustomerRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomerMapper {
    public Customer toDomain(CustomerRequest dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setPassword(dto.getPassword());

        Address address = new Address(
                dto.getAddress().getStreet(),
                dto.getAddress().getCity(),
                dto.getAddress().getState(),
                dto.getAddress().getZipCode()
        );

        customer.setAddresses(Collections.singletonList(address));

        return customer;
    }

    public Customer toDomain(UpdateCustomerRequest dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setCpf(dto.getCpf());

        if (dto.getAddress() != null) {
            Address address = new Address(
                    dto.getAddress().getStreet(),
                    dto.getAddress().getCity(),
                    dto.getAddress().getState(),
                    dto.getAddress().getZipCode()
            );

            customer.setAddresses(Collections.singletonList(address));
        }

        return customer;
    }

    public CustomerResponse toResponse(Customer customer) {
        List<Address> addresses = customer.getAddresses() != null && !customer.getAddresses().isEmpty() ? customer.getAddresses() : null;

        List<AddressResponse> addressesResponses;
        if (addresses != null) {
            addressesResponses = addresses.stream()
                    .map(address -> new AddressResponse(
                            address.getId(),
                            address.getStreet(),
                            address.getCity(),
                            address.getState(),
                            address.getZipCode()
                    ))
                    .toList();
        } else {
            addressesResponses = Collections.emptyList();
        }

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                addressesResponses
        );
    }
}

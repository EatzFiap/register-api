package com.eatz.presentation.web.customer.mapper;

import com.eatz.domain.address.CustomerAddressDetails;
import com.eatz.domain.customer.Customer;
import com.eatz.presentation.web.address.dto.CustomerAddressResponse;
import com.eatz.presentation.web.customer.dto.NewCustomerRequest;
import com.eatz.presentation.web.customer.dto.CustomerResponse;
import com.eatz.presentation.web.customer.dto.UpdateCustomerRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomerMapper {
    public Customer toDomain(NewCustomerRequest dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPassword(dto.getPassword());
        customer.setPhone(dto.getPhone());
        customer.setCpf(dto.getCpf());

        Address address = new Address();
        address.setStreet(dto.getAddress().street());
        address.setCity(dto.getAddress().city());
        address.setState(dto.getAddress().state());
        address.setZipCode(dto.getAddress().zipCode());

        customer.setAddresses(Collections.singletonList(address));

        return customer;
    }

    public Customer toDomain(UpdateCustomerRequest dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setCpf(dto.getCpf());
        customer.setProfileImageUrl(dto.getProfileImageUrl());

        return customer;
    }

    public CustomerResponse toResponse(Customer customer) {
        List<CustomerAddressDetails> addresses = customer.getAddresses() != null && !customer.getAddresses().isEmpty() ? customer.getAddresses() : null;

        List<CustomerAddressResponse> addressesResponses;
        if (addresses != null) {
            addressesResponses = addresses.stream()
                    .map(address -> new CustomerAddressResponse(
                            address.getId(),
                            address.getStreet(),
                            address.getNumber(),
                            address.getComplement(),
                            address.getNeighbourhood(),
                            address.getCity(),
                            address.getState(),
                            address.getZipCode(),
                            address.getNickname(),
                            address.isDefault()
                    ))
                    .toList();
        } else {
            addressesResponses = Collections.emptyList();
        }

        return new CustomerResponse(
                customer,
                addressesResponses
        );
    }
}

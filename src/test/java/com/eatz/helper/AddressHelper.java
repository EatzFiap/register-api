package com.eatz.helper;

import com.eatz.domain.address.Address;
import com.eatz.domain.address.CustomerAddressDetails;
import com.eatz.domain.customerAddresses.CustomerAddress;
import com.eatz.infrastructure.persistence.address.AddressEntity;
import com.eatz.infrastructure.persistence.customerAddress.CustomerAddressEntity;
import com.eatz.presentation.web.address.dto.AddressRequest;

import java.time.LocalDateTime;

public abstract class AddressHelper {

    public static Address createAddress() {
        Address address = new Address();
        address.setStreet("Rua das Flores");
        address.setNumber("123");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("12345-678");
        return address;
    }

    public static AddressEntity createAddressEntity() {
        AddressEntity entity = new AddressEntity();
        entity.setId(1L);
        entity.setStreet("Rua das Flores");
        entity.setNumber("123");
        entity.setCity("São Paulo");
        entity.setState("SP");
        entity.setZipCode("12345-678");
        entity.setCreatedAt("2025-01-01T12:00:00");
        entity.setDeleted(false);
        return entity;
    }

    public static AddressRequest createAddressRequest() {
        AddressRequest request = new AddressRequest();
        request.setStreet("Rua das Flores");
        request.setNumber("123");
        request.setNeighbourhood("Centro");
        request.setCity("São Paulo");
        request.setState("SP");
        request.setZipCode("12345-678");
        request.setNickname("Home");
        request.setDefaultAddress(true);
        return request;
    }

    public static CustomerAddressEntity createCustomerAddressEntity() {
        CustomerAddressEntity entity = new CustomerAddressEntity();
        entity.setAddressId(1L);
        entity.setNickname("Home");
        entity.setDefault(true);
        entity.setDeleted(false);
        entity.setAddress(createAddressEntity());
        return entity;
    }

    public static CustomerAddressDetails createCustomerAddressDetails() {
        CustomerAddressDetails address = new CustomerAddressDetails();
        address.setId(1L);
        address.setStreet("Rua das Flores");
        address.setNumber("123");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("12345-678");
        address.setNickname("Home");
        address.setDefault(true);
        return address;
    }

    public static CustomerAddress createCustomerAddress() {
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setId(1L);
        customerAddress.setCustomerId(1L);
        customerAddress.setAddressId(1L);
        customerAddress.setNickname("Home");
        customerAddress.setDefault(true);
        customerAddress.setCreatedAt(LocalDateTime.now());
        customerAddress.setDeleted(false);
        return customerAddress;
    }

}

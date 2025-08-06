package com.eatz.helper;

import com.eatz.domain.customer.Customer;
import com.eatz.infrastructure.persistence.customer.CustomerEntity;
import com.eatz.presentation.web.customer.dto.NewCustomerRequest;

import java.time.LocalDateTime;
import java.util.List;

import static com.eatz.helper.AddressHelper.*;

public abstract class CustomerHelper {

    public static CustomerEntity createCustomerEntity() {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(1L);
        entity.setName("João Silva");
        entity.setEmail("joao.silva@email.com");
        entity.setCpf("12345678901");
        entity.setPhone("11987654321");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setProfileImageUrl("https://example.com/profile.jpg");
        entity.setCustomerAddresses(List.of(
                createCustomerAddressEntity()
        ));
        return entity;
    }

    public static Customer createCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("João Silva");
        customer.setEmail("joao.silva@email.com");
        customer.setCpf("12345678901");
        customer.setPhone("11987654321");
        customer.setCreatedAt(LocalDateTime.now());
        customer.setProfileImageUrl("https://example.com/profile.jpg");
        customer.setAddresses(List.of(
                createCustomerAddressDetails()
        ));
        return customer;
    }

    public static NewCustomerRequest createNewCustomerRequest() {
        NewCustomerRequest request = new NewCustomerRequest();
        request.setName("Artur Silva");
        request.setEmail("artur.silva@email.com");
        request.setCpf("12345678901");
        request.setPhone("11987654321");
        request.setPassword("password123");
        request.setAddress(createAddressRequest());
        return request;
    }

}

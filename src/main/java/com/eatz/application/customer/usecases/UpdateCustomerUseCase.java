package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;

import java.util.UUID;

public class UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public UpdateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(Customer customer, Customer newData) {
        if (newData == null) {
            throw new IllegalArgumentException("New user data must not be null");
        }

        customer.setName(newData.getName());
        customer.setEmail(newData.getEmail());
        customer.setPhone(newData.getPhone());
        customer.setAddresses(newData.getAddresses());

        return customerRepository.save(customer);
    }
}

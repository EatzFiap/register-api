package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import com.eatz.domain.customer.exceptions.CustomerNotFoundException;

import java.util.List;
import java.util.UUID;

public class GetCustomerUseCase {

    private final CustomerRepository customerRepository;

    public GetCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(UUID id) throws CustomerNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }

        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("User not found with id: " + id));
    }

    public List<Customer> execute() throws CustomerNotFoundException {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("No users found");
        }
        return customers;
    }
}

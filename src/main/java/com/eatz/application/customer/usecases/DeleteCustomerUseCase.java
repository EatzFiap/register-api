package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;

import java.util.UUID;

public class DeleteCustomerUseCase {
    private final CustomerRepository customerRepository;

    public DeleteCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void execute(Customer customer) {
        customer.setDeleted(true);
        customerRepository.save(customer);
    }
}

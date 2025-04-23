package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;

import java.util.UUID;

public class UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public UpdateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(UUID id, Customer newData) {
        if (id == null || newData == null) {
            throw new IllegalArgumentException("Id and user data must not be null");
        }

        Customer customer = customerRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        customer.setName(newData.getName());
        customer.setEmail(newData.getEmail());
        customer.setPassword(newData.getPassword());
        customer.setAddress(newData.getAddress());

        return customerRepository.save(customer);
    }
}

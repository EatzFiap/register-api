package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import com.eatz.domain.customer.exceptions.CustomerNotFoundException;

import java.util.List;


public class GetCustomerUseCase {

    private final CustomerRepository customerRepository;

    public GetCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo.");
        }

        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Usuário não encontrado com o id: " + id));
    }

    public Customer execute(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }

        return customerRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomerNotFoundException("User not found with email: " + email));
    }

    public List<Customer> execute() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Nenhum usuário encontrado.");
        }
        return customers;
    }
}
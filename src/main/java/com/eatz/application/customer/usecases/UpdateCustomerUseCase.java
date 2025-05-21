package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import com.eatz.domain.customer.exceptions.CustomerNotFoundException;

import java.time.LocalDateTime;


public class UpdateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public UpdateCustomerUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer execute(Long id, Customer newData) {
        if (id == null) throw new IllegalArgumentException("ID não pode ser nulo.");
        if (newData == null) throw new IllegalArgumentException("Dados para atualização não podem ser nulos.");

        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Usuário não encontrado para atualização."));

        if (newData.getName() != null) existingCustomer.setName(newData.getName());
        if (newData.getEmail() != null) existingCustomer.setEmail(newData.getEmail());
        if (newData.getPhone() != null) existingCustomer.setPhone(newData.getPhone());
        existingCustomer.setUpdatedAt(LocalDateTime.now().toString());

        return customerRepository.save(existingCustomer);
    }
}
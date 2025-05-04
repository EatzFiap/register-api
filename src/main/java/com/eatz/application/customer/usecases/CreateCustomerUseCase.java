package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import com.eatz.domain.customer.exceptions.CustomerAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

public class CreateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateCustomerUseCase(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer execute(Customer customer) {
        if (customer == null)
            throw new IllegalArgumentException("Usuário não pode ser nulo.");
        if (customer.getEmail() == null || customer.getEmail().isBlank())
            throw new IllegalArgumentException("E-mail é obrigatório.");
        if (customerRepository.existsByEmailAndIsDeletedFalse(customer.getEmail()))
            throw new CustomerAlreadyExistsException("Já existe um usuário com este e-mail.");

        String encodedPassword = passwordEncoder.encode(Objects.requireNonNull(customer.getPassword(), "Senha é obrigatória."));
        customer.setPassword(encodedPassword);

        return customerRepository.save(customer);
    }
}
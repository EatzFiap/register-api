package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateCustomerUseCase(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Customer execute(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("User must not be null");
        }

        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);

        return customerRepository.save(customer);
    }
}

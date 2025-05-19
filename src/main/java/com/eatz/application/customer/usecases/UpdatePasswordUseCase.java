package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import com.eatz.domain.customer.exceptions.CustomerNotFoundException;
import com.eatz.domain.customer.exceptions.InvalidPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


public class UpdatePasswordUseCase {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public UpdatePasswordUseCase(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(Customer customer, String oldPassword, String newPassword) {

        if (!passwordEncoder.matches(oldPassword, customer.getPassword())) {
            throw new InvalidPasswordException();
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedNewPassword);
        customerRepository.save(customer);
    }

}
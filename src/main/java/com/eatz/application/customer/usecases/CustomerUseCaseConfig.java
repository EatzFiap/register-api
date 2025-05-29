package com.eatz.application.customer.usecases;

import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.CustomerRepository;
import com.eatz.shared.usecases.UpdateUserPasswordUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomerUseCaseConfig {

    @Bean
    public CreateCustomerUseCase createUserUseCase(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        return new CreateCustomerUseCase(customerRepository, passwordEncoder);
    }

    @Bean
    public DeleteCustomerUseCase deleteUserUseCase(CustomerRepository customerRepository) {
        return new DeleteCustomerUseCase(customerRepository);
    }

    @Bean
    public UpdateCustomerUseCase updateUserUseCase(CustomerRepository customerRepository) {
        return new UpdateCustomerUseCase(customerRepository);
    }

    @Bean
    public UpdateUserPasswordUseCase<Customer> updateCustomerPasswordUseCase(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        return new UpdateUserPasswordUseCase<>(customerRepository, passwordEncoder);
    }

    @Bean
    public GetCustomerUseCase getUserUseCase(CustomerRepository customerRepository) {
        return new GetCustomerUseCase(customerRepository);
    }
}

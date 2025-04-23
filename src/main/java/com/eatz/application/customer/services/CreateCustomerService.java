package com.eatz.application.customer.services;

import com.eatz.application.customer.usecases.CreateCustomerUseCase;
import com.eatz.domain.customer.Customer;
import org.springframework.stereotype.Service;

@Service
public class CreateCustomerService {

    private final CreateCustomerUseCase useCase;

    public CreateCustomerService(CreateCustomerUseCase useCase) {
        this.useCase = useCase;
    }

    public Customer execute(Customer customer) {
        return useCase.execute(customer);
    }
}
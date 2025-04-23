package com.eatz.application.customer.services;

import com.eatz.application.customer.usecases.UpdateCustomerUseCase;
import com.eatz.domain.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateCustomerService {

    private final UpdateCustomerUseCase useCase;

    public UpdateCustomerService(UpdateCustomerUseCase useCase) {
        this.useCase = useCase;
    }

    public Customer execute(UUID id, Customer newData) {
        return useCase.execute(id, newData);
    }
}

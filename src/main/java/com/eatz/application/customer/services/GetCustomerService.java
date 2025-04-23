package com.eatz.application.customer.services;

import com.eatz.application.customer.usecases.GetCustomerUseCase;
import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetCustomerService {

    private final GetCustomerUseCase useCase;

    public GetCustomerService(GetCustomerUseCase useCase) {
        this.useCase = useCase;
    }

    public Customer execute(UUID id) throws CustomerNotFoundException {
        return useCase.execute(id);
    }

    public List<Customer> execute() throws CustomerNotFoundException {
        return useCase.execute();
    }
}
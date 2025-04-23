package com.eatz.application.customer.services;

import com.eatz.application.customer.usecases.DeleteCustomerUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCustomerService {
    public final DeleteCustomerUseCase deleteCustomerUseCase;

    public DeleteCustomerService(DeleteCustomerUseCase useCase) {
        this.deleteCustomerUseCase = useCase;
    }

    public void execute(UUID id) {
        deleteCustomerUseCase.execute(id);
    }
}

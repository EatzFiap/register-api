package com.eatz.application.customer.services;

import com.eatz.application.customer.usecases.CreateCustomerUseCase;
import com.eatz.application.customer.usecases.DeleteCustomerUseCase;
import com.eatz.application.customer.usecases.GetCustomerUseCase;
import com.eatz.application.customer.usecases.UpdateCustomerUseCase;
import com.eatz.domain.customer.Customer;
import com.eatz.domain.customer.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;

    public CustomerService(
            CreateCustomerUseCase createCustomerUseCase,
            DeleteCustomerUseCase deleteCustomerUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            GetCustomerUseCase getCustomerUseCase
    ) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
    }

    public Customer createCustomer(Customer customer) {
        return createCustomerUseCase.execute(customer);
    }

    public Customer getCustomer(UUID customerId) throws CustomerNotFoundException {
        return getCustomerUseCase.execute(customerId);
    }

    public Customer updateCustomer(UUID customerId, Customer newData) throws CustomerNotFoundException {
        Customer customer = getCustomerUseCase.execute(customerId);
        return updateCustomerUseCase.execute(customer, newData);
    }

    public void deleteCustomer(UUID customerId) throws CustomerNotFoundException {
        Customer customer = getCustomerUseCase.execute(customerId);
        deleteCustomerUseCase.execute(customer);
    }

}
package com.eatz.application.customer.services;

import com.eatz.application.address.usecases.CreateAddressUseCase;
import com.eatz.application.customer.usecases.CreateCustomerUseCase;
import com.eatz.application.customer.usecases.DeleteCustomerUseCase;
import com.eatz.application.customer.usecases.GetCustomerUseCase;
import com.eatz.application.customer.usecases.UpdateCustomerUseCase;
import com.eatz.application.customerAddresses.usecases.AssociateAddressToCustomerUseCase;
import com.eatz.domain.address.Address;
import com.eatz.domain.customer.Customer;
import com.eatz.domain.customerAddresses.CustomerAddress;
import org.springframework.stereotype.Service;



@Service
public class CustomerService {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final CreateAddressUseCase createAddressUseCase;
    private final AssociateAddressToCustomerUseCase associateAddressToCustomerUseCase;

    public CustomerService(
            CreateCustomerUseCase createCustomerUseCase,
            DeleteCustomerUseCase deleteCustomerUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            GetCustomerUseCase getCustomerUseCase,
            CreateAddressUseCase createAddressUseCase,
            AssociateAddressToCustomerUseCase associateAddressToCustomerUseCase
    ) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
        this.createAddressUseCase = createAddressUseCase;
        this.associateAddressToCustomerUseCase = associateAddressToCustomerUseCase;
    }

    public Customer createCustomer(Customer customer) {
        Customer createdCustomer = createCustomerUseCase.execute(customer);

        if (customer.getAddresses() != null) {
            for (Address address : customer.getAddresses()) {
                Address savedAddress = createAddressUseCase.execute(address);
                associateAddressToCustomerUseCase.execute(createdCustomer.getId(), savedAddress.getId(), savedAddress);
            }
        }

        return createdCustomer;
    }

    public Customer getCustomer(Long customerId) {
        return getCustomerUseCase.execute(customerId);
    }

    public Customer updateCustomer(Long customerId, Customer newData) {
        return updateCustomerUseCase.execute(customerId, newData);
    }

    public void deleteCustomer(Long customerId) {
        deleteCustomerUseCase.execute(customerId);
    }
}
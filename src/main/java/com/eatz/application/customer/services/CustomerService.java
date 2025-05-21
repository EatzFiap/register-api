package com.eatz.application.customer.services;

import com.eatz.application.address.usecases.CreateAddressUseCase;
import com.eatz.application.customer.usecases.*;
import com.eatz.application.customerAddresses.usecases.AssociateAddressToCustomerUseCase;
import com.eatz.domain.address.Address;
import com.eatz.domain.customer.Customer;
import com.eatz.infrastructure.security.JwtUtil;
import com.eatz.shared.dto.PasswordUpdateRequest;
import com.eatz.shared.usecases.UpdateUserPasswordUseCase;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final CreateAddressUseCase createAddressUseCase;
    private final AssociateAddressToCustomerUseCase associateAddressToCustomerUseCase;
    private final UpdateUserPasswordUseCase<Customer> updatePasswordUseCase;
    private final JwtUtil jwtUtil;

    public CustomerService(
            CreateCustomerUseCase createCustomerUseCase,
            DeleteCustomerUseCase deleteCustomerUseCase,
            UpdateCustomerUseCase updateCustomerUseCase,
            GetCustomerUseCase getCustomerUseCase,
            CreateAddressUseCase createAddressUseCase,
            AssociateAddressToCustomerUseCase associateAddressToCustomerUseCase,
            UpdateUserPasswordUseCase<Customer> updatePasswordUseCase,
            JwtUtil jwtUtil
    ) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.deleteCustomerUseCase = deleteCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
        this.createAddressUseCase = createAddressUseCase;
        this.associateAddressToCustomerUseCase = associateAddressToCustomerUseCase;
        this.updatePasswordUseCase = updatePasswordUseCase;
        this.jwtUtil = jwtUtil;
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
        Customer updatedCustomer = updateCustomerUseCase.execute(customerId, newData);
        if (newData.getAddresses() != null) {
            for (Address address : newData.getAddresses()) {
                Address savedAddress = createAddressUseCase.execute(address);
                associateAddressToCustomerUseCase.execute(updatedCustomer.getId(), savedAddress.getId(), savedAddress);
            }
        }
        return updatedCustomer;
    }

    public void deleteCustomer(Long customerId) {
        deleteCustomerUseCase.execute(customerId);
    }

    public void updatePassword(String token, PasswordUpdateRequest request) {

        String email = jwtUtil.extractUsername(token);
        Customer customer = getCustomerUseCase.execute(email);
        updatePasswordUseCase.execute(customer, request.getOldPassword(), request.getNewPassword());

    }

    public Customer getCustomerByUsername(String token) {
        String email = jwtUtil.extractUsername(token);
        return getCustomerUseCase.execute(email);
    }
}
package com.eatz.application.customer.services;

import com.eatz.application.address.usecases.SaveAddressUseCase;
import com.eatz.application.customer.usecases.CreateCustomerUseCase;
import com.eatz.application.customerAddresses.usecases.AssociateAddressToCustomerUseCase;
import com.eatz.domain.address.Address;
import com.eatz.domain.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CreateCustomerUseCase createCustomerUseCase;

    @Mock
    private SaveAddressUseCase saveAddressUseCase;

    @Mock
    private AssociateAddressToCustomerUseCase associateAddressToCustomerUseCase;

    @InjectMocks
    private CustomerService customerService;

    @Nested
    @DisplayName("Create Customer")
    class CreateCustomer {

    }

}
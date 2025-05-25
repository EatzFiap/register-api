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

        @Test
        void createCustomer_createsCustomerWithoutAddresses() {
            Customer customer = new Customer();
            customer.setAddresses(null);

            Customer createdCustomer = new Customer();
            createdCustomer.setId(1L);

            when(createCustomerUseCase.execute(customer)).thenReturn(createdCustomer);

            Customer result = customerService.createCustomer(customer);

            assertEquals(createdCustomer, result);
            verify(createCustomerUseCase).execute(customer);
            verifyNoInteractions(saveAddressUseCase, associateAddressToCustomerUseCase);
        }

        @Test
        void createCustomer_createsCustomerWithAddresses() {
            Customer customer = new Customer();
            Address address1 = new Address();
            Address address2 = new Address();
            customer.setAddresses(List.of(address1, address2));

            Customer createdCustomer = new Customer();
            createdCustomer.setId(1L);

            Address savedAddress1 = new Address();
            savedAddress1.setId(10L);
            Address savedAddress2 = new Address();
            savedAddress2.setId(20L);

            when(createCustomerUseCase.execute(customer)).thenReturn(createdCustomer);
            when(saveAddressUseCase.execute(address1)).thenReturn(savedAddress1);
            when(saveAddressUseCase.execute(address2)).thenReturn(savedAddress2);

            Customer result = customerService.createCustomer(customer);

            assertEquals(createdCustomer, result);
            verify(createCustomerUseCase).execute(customer);
            verify(saveAddressUseCase).execute(address1);
            verify(saveAddressUseCase).execute(address2);
            verify(associateAddressToCustomerUseCase).execute(createdCustomer.getId(), savedAddress1.getId(), savedAddress1);
            verify(associateAddressToCustomerUseCase).execute(createdCustomer.getId(), savedAddress2.getId(), savedAddress2);
        }

        @Test
        void createCustomer_handlesEmptyAddressList() {
            Customer customer = new Customer();
            customer.setAddresses(Collections.emptyList());

            Customer createdCustomer = new Customer();
            createdCustomer.setId(1L);

            when(createCustomerUseCase.execute(customer)).thenReturn(createdCustomer);

            Customer result = customerService.createCustomer(customer);

            assertEquals(createdCustomer, result);
            verify(createCustomerUseCase).execute(customer);
            verifyNoInteractions(saveAddressUseCase, associateAddressToCustomerUseCase);
        }
        
    }

}
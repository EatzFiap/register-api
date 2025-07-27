package com.eatz.infrastructure.persistence.customer;

import com.eatz.domain.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.eatz.helper.CustomerHelper.createCustomer;
import static com.eatz.helper.CustomerHelper.createCustomerEntity;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerEntityMapperTest {

    private final CustomerEntityMapper customerEntityMapper = new CustomerEntityMapper();

    @Nested
    @DisplayName("Convert CustomerEntity to Customer")
    class ToDomain {

        @Test
        @DisplayName("Converts CustomerEntity to Customer successfully when entity is valid")
        void convertsCustomerEntityToCustomerSuccessfullyWhenEntityIsValid() {
            CustomerEntity entity = createCustomerEntity();

            Customer customer = customerEntityMapper.toDomain(entity);

            assertNotNull(customer);
            assertEquals(entity.getId(), customer.getId());
            assertEquals(entity.getName(), customer.getName());
            assertEquals(entity.getEmail(), customer.getEmail());

            assertEquals(1, customer.getAddresses().size());
            assertEquals("Home", customer.getAddresses().getFirst().getNickname());
            assertTrue(customer.getAddresses().getFirst().isDefault());
        }

        @Test
        @DisplayName("Returns Customer with empty address list when CustomerEntity has no addresses")
        void returnsCustomerWithEmptyAddressListWhenCustomerEntityHasNoAddresses() {
            CustomerEntity entity = createCustomerEntity();
            entity.setCustomerAddresses(Collections.emptyList());

            Customer customer = customerEntityMapper.toDomain(entity);

            assertNotNull(customer);
            assertEquals(entity.getId(), customer.getId());
            assertEquals(entity.getName(), customer.getName());
            assertEquals(entity.getEmail(), customer.getEmail());
            assertTrue(customer.getAddresses().isEmpty());
        }

        @Test
        @DisplayName("Filters out deleted customer addresses when converting CustomerEntity to Customer")
        void filtersOutDeletedCustomerAddressesWhenConvertingCustomerEntityToCustomer() {
            CustomerEntity entity = createCustomerEntity();
            entity.getCustomerAddresses().getFirst().setDeleted(true);

            Customer customer = customerEntityMapper.toDomain(entity);

            assertNotNull(customer);
            assertEquals(entity.getId(), customer.getId());
            assertEquals(entity.getName(), customer.getName());
            assertEquals(entity.getEmail(), customer.getEmail());
            assertTrue(customer.getAddresses().isEmpty());
        }

        @Test
        @DisplayName("Filters out deleted address when converting CustomerEntity to Customer")
        void filtersOutDeletedAddressWhenConvertingCustomerEntityToCustomer() {
            CustomerEntity entity = createCustomerEntity();
            entity.getCustomerAddresses().getFirst().getAddress().setDeleted(true);

            Customer customer = customerEntityMapper.toDomain(entity);

            assertNotNull(customer);
            assertEquals(entity.getId(), customer.getId());
            assertEquals(entity.getName(), customer.getName());
            assertEquals(entity.getEmail(), customer.getEmail());
            assertTrue(customer.getAddresses().isEmpty());
        }

    }

    @Nested
    @DisplayName("Convert Customer to CustomerEntity")
    class ToEntity {

        @Test
        @DisplayName("Converts Customer to CustomerEntity successfully when customer is valid")
        void convertsCustomerToCustomerEntitySuccessfullyWhenCustomerIsValid() {
            Customer customer = createCustomer();

            CustomerEntity entity = customerEntityMapper.toEntity(customer);

            assertNotNull(entity);
            assertEquals(customer.getId(), entity.getId());
            assertEquals(customer.getName(), entity.getName());
            assertEquals(customer.getEmail(), entity.getEmail());
            assertEquals(customer.getPassword(), entity.getPassword());
            assertEquals(customer.getCpf(), entity.getCpf());
            assertEquals(customer.getPhone(), entity.getPhone());
            assertEquals(customer.getCreatedAt(), entity.getCreatedAt());
            assertEquals(customer.getUpdatedAt(), entity.getUpdatedAt());
            assertEquals(customer.isDeleted(), entity.isDeleted());
            assertEquals(customer.getProfileImageUrl(), entity.getProfileImageUrl());
        }

    }

}
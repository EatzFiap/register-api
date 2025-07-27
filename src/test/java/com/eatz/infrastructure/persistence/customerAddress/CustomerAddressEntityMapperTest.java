package com.eatz.infrastructure.persistence.customerAddress;

import com.eatz.domain.customerAddresses.CustomerAddress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.eatz.helper.AddressHelper.createCustomerAddress;
import static com.eatz.helper.AddressHelper.createCustomerAddressEntity;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerAddressEntityMapperTest {

    @Nested
    @DisplayName("Convert CustomerAddress to CustomerAddressEntity")
    class ToEntity {

        @Test
        @DisplayName("Converts CustomerAddress to CustomerAddressEntity successfully")
        void convertsCustomerAddressToCustomerAddressEntitySuccessfully() {
            CustomerAddress customerAddress = createCustomerAddress();

            CustomerAddressEntity entity = CustomerAddressEntityMapper.toEntity(customerAddress);

            assertNotNull(entity);
            assertEquals(customerAddress.getId(), entity.getIdCustomerAddress());
            assertEquals(customerAddress.getCustomerId(), entity.getCustomerId());
            assertEquals(customerAddress.getAddressId(), entity.getAddressId());
            assertEquals(customerAddress.getNickname(), entity.getNickname());
            assertEquals(customerAddress.isDefault(), entity.isDefault());
            assertEquals(customerAddress.isDeleted(), entity.isDeleted());
            assertEquals(customerAddress.getCreatedAt(), entity.getCreatedAt());
            assertNull(entity.getUpdatedAt());
        }

        @Test
        @DisplayName("Returns null when CustomerAddress is null")
        void returnsNullWhenCustomerAddressIsNull() {
            CustomerAddressEntity entity = CustomerAddressEntityMapper.toEntity(null);

            assertNull(entity);
        }

    }

    @Nested
    @DisplayName("Convert CustomerAddressEntity to CustomerAddress")
    class ToDomain {

        @Test
        @DisplayName("Converts CustomerAddressEntity to CustomerAddress successfully when entity is valid")
        void convertsCustomerAddressEntityToCustomerAddressSuccessfullyWhenEntityIsValid() {
            CustomerAddressEntity entity = createCustomerAddressEntity();

            CustomerAddress domain = CustomerAddressEntityMapper.toDomain(entity);

            assertNotNull(domain);
            assertEquals(entity.getIdCustomerAddress(), domain.getId());
            assertEquals(entity.getCustomerId(), domain.getCustomerId());
            assertEquals(entity.getAddressId(), domain.getAddressId());
            assertEquals(entity.getNickname(), domain.getNickname());
            assertEquals(entity.isDefault(), domain.isDefault());
            assertEquals(entity.isDeleted(), domain.isDeleted());
        }

        @Test
        @DisplayName("Returns null when CustomerAddressEntity is null")
        void returnsNullWhenCustomerAddressEntityIsNull() {
            CustomerAddress domain = CustomerAddressEntityMapper.toDomain(null);

            assertNull(domain);
        }

    }

}
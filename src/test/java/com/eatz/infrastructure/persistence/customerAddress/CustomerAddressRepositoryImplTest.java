package com.eatz.infrastructure.persistence.customerAddress;

import com.eatz.domain.customerAddresses.CustomerAddress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.eatz.helper.AddressHelper.createCustomerAddress;
import static com.eatz.helper.AddressHelper.createCustomerAddressEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerAddressRepositoryImplTest {

    @Mock
    private JpaCustomerAddressRepository jpaRepository;

    @InjectMocks
    private CustomerAddressRepositoryImpl customerAddressRepository;

    @Test
    @DisplayName("Saves CustomerAddress successfully and returns the saved CustomerAddress")
    void savesCustomerAddressSuccessfullyAndReturnsSavedCustomerAddress() {
        CustomerAddress customerAddress = createCustomerAddress();
        CustomerAddressEntity savedEntity = createCustomerAddressEntity();

        when(jpaRepository.save(any(CustomerAddressEntity.class))).thenReturn(savedEntity);

        CustomerAddress result = customerAddressRepository.save(customerAddress);

        assertNotNull(result);
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "customerId", "createdAt", "updatedAt")
                .isEqualTo(customerAddress);
        verify(jpaRepository, times(1)).save(any(CustomerAddressEntity.class));
    }

    @Test
    @DisplayName("Finds CustomerAddress by ID successfully")
    void findsCustomerAddressByIdSuccessfully() {
        Long id = 1L;
        CustomerAddressEntity entity = new CustomerAddressEntity();
        CustomerAddress customerAddress = new CustomerAddress();

        when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));

        Optional<CustomerAddress> result = customerAddressRepository.findById(id);

        assertTrue(result.isPresent());
        assertThat(result.get())
                .usingRecursiveComparison()
                .isEqualTo(customerAddress);
        verify(jpaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Returns empty when CustomerAddress is not found by ID")
    void returnsEmptyWhenCustomerAddressIsNotFoundById() {
        Long id = 1L;

        when(jpaRepository.findById(id)).thenReturn(Optional.empty());

        Optional<CustomerAddress> result = customerAddressRepository.findById(id);

        assertTrue(result.isEmpty());
        verify(jpaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Finds all CustomerAddresses by Customer ID successfully")
    void findsAllCustomerAddressesByCustomerIdSuccessfully() {
        Long customerId = 1L;
        List<CustomerAddressEntity> entities = List.of(new CustomerAddressEntity(), new CustomerAddressEntity());
        List<CustomerAddress> customerAddresses = List.of(new CustomerAddress(), new CustomerAddress());

        when(jpaRepository.findByCustomerIdAndIsDeletedFalse(customerId)).thenReturn(entities);

        List<CustomerAddress> result = customerAddressRepository.findByCustomerId(customerId);

        assertNotNull(result);
        assertEquals(customerAddresses.size(), result.size());
        assertThat(result)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(customerAddresses);
        verify(jpaRepository, times(1)).findByCustomerIdAndIsDeletedFalse(customerId);
    }

    @Test
    @DisplayName("Deletes CustomerAddress successfully")
    void deletesCustomerAddressSuccessfully() {
        CustomerAddress customerAddress = new CustomerAddress();

        doNothing().when(jpaRepository).delete(any(CustomerAddressEntity.class));

        customerAddressRepository.delete(customerAddress);

        verify(jpaRepository, times(1)).delete(any(CustomerAddressEntity.class));
    }

}
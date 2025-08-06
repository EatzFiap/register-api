package com.eatz.infrastructure.persistence.customer;

import com.eatz.domain.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.eatz.helper.CustomerHelper.createCustomer;
import static com.eatz.helper.CustomerHelper.createCustomerEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryImplTest {

    @Mock
    private JpaCustomerRepository jpaRepository;

    @Mock
    private CustomerEntityMapper mapper;

    @InjectMocks
    private CustomerRepositoryImpl customerRepository;

    @Nested
    @DisplayName("Find by ID")
    class FindById {

        @Test
        @DisplayName("Finds Customer by ID successfully when ID exists")
        void findsCustomerByIdSuccessfullyWhenIdExists() {
            Long id = 1L;
            CustomerEntity entity = createCustomerEntity();
            when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));
            when(mapper.toDomain(entity)).thenReturn(new Customer());

            Optional<Customer> result = customerRepository.findById(id);

            assertTrue(result.isPresent());
            verify(jpaRepository, times(1)).findById(id);
        }

        @Test
        @DisplayName("Returns empty Optional when Customer ID does not exist")
        void returnsEmptyOptionalWhenCustomerIdDoesNotExist() {
            Long id = 1L;
            when(jpaRepository.findById(id)).thenReturn(Optional.empty());

            Optional<Customer> result = customerRepository.findById(id);

            assertFalse(result.isPresent());
            verify(jpaRepository, times(1)).findById(id);
        }

    }

    @Nested
    @DisplayName("Save Customer")
    class SaveCustomer {

        @Test
        @DisplayName("Saves Customer successfully and returns the saved Customer")
        void savesCustomerSuccessfullyAndReturnsSavedCustomer() {
            Customer customer = createCustomer();
            CustomerEntity entity = createCustomerEntity();

            when(mapper.toEntity(customer)).thenReturn(entity);
            when(jpaRepository.save(entity)).thenReturn(entity);
            when(mapper.toDomain(entity)).thenReturn(customer);

            Customer result = customerRepository.save(customer);

            assertNotNull(result);
            assertEquals(customer, result);
            verify(jpaRepository, times(1)).save(entity);
        }

    }

    @Nested
    @DisplayName("Exists by Email and Is Deleted is False")
    class ExistsByEmailAndIsDeletedFalse {

        @Test
        @DisplayName("Returns true when Customer with email exists and is not deleted")
        void returnsTrueWhenCustomerWithEmailExistsAndIsNotDeleted() {
            String email = "test@example.com";
            when(jpaRepository.existsByEmailAndIsDeletedFalse(email)).thenReturn(true);

            boolean result = customerRepository.existsByEmailAndIsDeletedFalse(email);

            assertTrue(result);
            verify(jpaRepository, times(1)).existsByEmailAndIsDeletedFalse(email);
        }

        @Test
        @DisplayName("Returns false when Customer with email does not exist or is deleted")
        void returnsFalseWhenCustomerWithEmailDoesNotExistOrIsDeleted() {
            String email = "test@example.com";
            when(jpaRepository.existsByEmailAndIsDeletedFalse(email)).thenReturn(false);

            boolean result = customerRepository.existsByEmailAndIsDeletedFalse(email);

            assertFalse(result);
            verify(jpaRepository, times(1)).existsByEmailAndIsDeletedFalse(email);
        }

    }

    @Nested
    @DisplayName("Find by Email and Is Deleted is False")
    class FindByEmailAndIsDeletedFalse {

        @Test
        @DisplayName("Finds Customer by email successfully when email exists and is not deleted")
        void findsCustomerByEmailSuccessfullyWhenEmailExistsAndIsNotDeleted() {
            String email = "joao.silva@email.com";
            CustomerEntity entity = createCustomerEntity();
            when(jpaRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(Optional.of(entity));
            when(mapper.toDomain(entity)).thenReturn(new Customer());

            Optional<Customer> result = customerRepository.findByEmailAndIsDeletedFalse(email);

            assertTrue(result.isPresent());
            verify(jpaRepository, times(1)).findByEmailAndIsDeletedFalse(email);
        }

        @Test
        @DisplayName("Returns empty Optional when Customer email does not exist or is deleted")
        void returnsEmptyOptionalWhenCustomerEmailDoesNotExistOrIsDeleted() {
            String email = "test@example.com";
            when(jpaRepository.findByEmailAndIsDeletedFalse(email)).thenReturn(Optional.empty());

            Optional<Customer> result = customerRepository.findByEmailAndIsDeletedFalse(email);

            assertFalse(result.isPresent());
            verify(jpaRepository, times(1)).findByEmailAndIsDeletedFalse(email);
        }

    }

    @Nested
    @DisplayName("Find by ID and Is Deleted is False")
    class FindByIdAndIsDeletedFalse {

        @Test
        @DisplayName("Finds Customer by ID successfully when ID exists and is not deleted")
        void findsCustomerByIdSuccessfullyWhenIdExistsAndIsNotDeleted() {
            Long id = 1L;
            CustomerEntity entity = createCustomerEntity();
            when(jpaRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.of(entity));
            when(mapper.toDomain(entity)).thenReturn(new Customer());

            Optional<Customer> result = customerRepository.findByIdAndIsDeletedFalse(id);

            assertTrue(result.isPresent());
            verify(jpaRepository, times(1)).findByIdAndIsDeletedFalse(id);
        }

        @Test
        @DisplayName("Returns empty Optional when Customer ID does not exist or is deleted")
        void returnsEmptyOptionalWhenCustomerIdDoesNotExistOrIsDeleted() {
            Long id = 1L;
            when(jpaRepository.findByIdAndIsDeletedFalse(id)).thenReturn(Optional.empty());

            Optional<Customer> result = customerRepository.findByIdAndIsDeletedFalse(id);

            assertFalse(result.isPresent());
            verify(jpaRepository, times(1)).findByIdAndIsDeletedFalse(id);
        }

    }

    @Nested
    @DisplayName("Find All Customers")
    class FindAllCustomers {

        @Test
        @DisplayName("Finds all Customers successfully")
        void findsAllCustomersSuccessfully() {
            List<CustomerEntity> entities = List.of(createCustomerEntity(), createCustomerEntity());
            List<Customer> customers = List.of(createCustomer(), createCustomer());
            when(jpaRepository.findAll()).thenReturn(entities);
            when(mapper.toDomain(entities.get(0))).thenReturn(customers.get(0));
            when(mapper.toDomain(entities.get(1))).thenReturn(customers.get(1));

            List<Customer> result = customerRepository.findAll();

            assertNotNull(result);
            assertEquals(customers.size(), result.size());
            assertEquals(customers, result);
            verify(jpaRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Returns empty list when no Customers exist")
        void returnsEmptyListWhenNoCustomersExist() {
            when(jpaRepository.findAll()).thenReturn(List.of());

            List<Customer> result = customerRepository.findAll();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(jpaRepository, times(1)).findAll();
        }

    }

}
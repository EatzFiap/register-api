package com.eatz.infrastructure.persistence.address;

import com.eatz.domain.address.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.eatz.helper.AddressHelper.createAddress;
import static com.eatz.helper.AddressHelper.createAddressEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressRepositoryImplTest {

    @Mock
    private JpaAddressRepository jpaRepository;

    @InjectMocks
    private AddressRepositoryImpl addressRepository;

    @Nested
    class Save {

        @Test
        @DisplayName("Saves Address successfully and returns the saved Address")
        void savesAddressSuccessfullyAndReturnsSavedAddress() {
            Address address = createAddress();
            AddressEntity savedEntity = createAddressEntity();

            when(jpaRepository.save(any(AddressEntity.class))).thenReturn(savedEntity);

            Address result = addressRepository.save(address);

            verify(jpaRepository, times(1)).save(any(AddressEntity.class));

            assertNotNull(result);
            assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id", "createdAt", "updatedAt")
                .isEqualTo(address);

        }

    }

    @Nested
    class FindById {

        @Test
        @DisplayName("Finds Address by ID successfully when ID exists")
        void findsAddressByIdSuccessfullyWhenIdExists() {
            Long id = 1L;
            AddressEntity entity = createAddressEntity();
            when(jpaRepository.findById(id)).thenReturn(Optional.of(entity));

            Optional<Address> result = addressRepository.findById(id);

            assertTrue(result.isPresent());
            assertThat(result.get())
                    .usingRecursiveComparison()
                    .isEqualTo(AddressEntityMapper.toDomain(entity));
            verify(jpaRepository, times(1)).findById(id);
        }

        @Test
        @DisplayName("Returns empty Optional when Address ID does not exist")
        void returnsEmptyOptionalWhenAddressIdDoesNotExist() {
            Long id = 1L;
            when(jpaRepository.findById(id)).thenReturn(Optional.empty());

            Optional<Address> result = addressRepository.findById(id);

            assertFalse(result.isPresent());
            verify(jpaRepository, times(1)).findById(id);
        }

    }

    @Nested
    class FindAll {

        @Test
        @DisplayName("Finds all Addresses successfully")
        void findsAllAddressesSuccessfully() {
            List<AddressEntity> entities = List.of(createAddressEntity(), createAddressEntity());
            when(jpaRepository.findAll()).thenReturn(entities);

            List<Address> result = addressRepository.findAll();

            assertNotNull(result);
            assertEquals(entities.size(), result.size());
            verify(jpaRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Returns empty list when no Addresses exist")
        void returnsEmptyListWhenNoAddressesExist() {
            when(jpaRepository.findAll()).thenReturn(List.of());

            List<Address> result = addressRepository.findAll();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(jpaRepository, times(1)).findAll();
        }

    }

    @Nested
    class Delete {

        @Test
        @DisplayName("Deletes Address successfully")
        void deletesAddressSuccessfully() {
            Address address = createAddress();

            doNothing().when(jpaRepository).delete(any(AddressEntity.class));

            addressRepository.delete(address);

            verify(jpaRepository, times(1)).delete(any(AddressEntity.class));
        }

    }

}
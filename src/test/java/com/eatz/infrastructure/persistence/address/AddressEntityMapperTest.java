package com.eatz.infrastructure.persistence.address;

import com.eatz.domain.address.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.eatz.helper.AddressHelper.createAddress;
import static com.eatz.helper.AddressHelper.createAddressEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class AddressEntityMapperTest {

    @Nested
    @DisplayName("Convert AddressEntity to Address")
    class ToDomain {

        @Test
        @DisplayName("Converts AddressEntity to Address successfully when entity is valid")
        void convertsAddressEntityToAddressSuccessfullyWhenEntityIsValid() {
            AddressEntity entity = createAddressEntity();

            Address address = AddressEntityMapper.toDomain(entity);

            assertNotNull(address);
            assertThat(address)
                    .extracting(
                            Address::getId,
                            Address::getStreet,
                            Address::getNumber,
                            Address::getComplement,
                            Address::getCity,
                            Address::getNeighbourhood,
                            Address::getState,
                            Address::getZipCode,
                            Address::getCreatedAt,
                            Address::getUpdatedAt,
                            Address::isDeleted
                    )
                    .containsExactly(
                            entity.getId(),
                            entity.getStreet(),
                            entity.getNumber(),
                            entity.getComplement(),
                            entity.getCity(),
                            entity.getNeighbourhood(),
                            entity.getState(),
                            entity.getZipCode(),
                            entity.getCreatedAt(),
                            entity.getUpdatedAt(),
                            entity.isDeleted()
                    );
        }

        @Test
        @DisplayName("Returns null when AddressEntity is null")
        void returnsNullWhenAddressEntityIsNull() {
            Address address = AddressEntityMapper.toDomain(null);

            assertNull(address);
        }

    }

    @Nested
    @DisplayName("Convert Address to AddressEntity")
    class ToEntity {

        @Test
        @DisplayName("Converts Address to AddressEntity successfully when address is valid")
        void convertsAddressToAddressEntitySuccessfullyWhenAddressIsValid() {
            Address address = createAddress();

            AddressEntity entity = AddressEntityMapper.toEntity(address);

            assertNotNull(entity);
            assertThat(entity)
                    .extracting(
                            AddressEntity::getId,
                            AddressEntity::getStreet,
                            AddressEntity::getNumber,
                            AddressEntity::getComplement,
                            AddressEntity::getCity,
                            AddressEntity::getNeighbourhood,
                            AddressEntity::getState,
                            AddressEntity::getZipCode,
                            AddressEntity::getCreatedAt,
                            AddressEntity::getUpdatedAt,
                            AddressEntity::isDeleted
                    )
                    .containsExactly(
                            address.getId(),
                            address.getStreet(),
                            address.getNumber(),
                            address.getComplement(),
                            address.getCity(),
                            address.getNeighbourhood(),
                            address.getState(),
                            address.getZipCode(),
                            address.getCreatedAt(),
                            address.getUpdatedAt(),
                            address.isDeleted()
                    );
        }

        @Test
        @DisplayName("Returns null when Address is null")
        void returnsNullWhenAddressIsNull() {
            AddressEntity entity = AddressEntityMapper.toEntity(null);

            assertNull(entity);
        }

    }

}
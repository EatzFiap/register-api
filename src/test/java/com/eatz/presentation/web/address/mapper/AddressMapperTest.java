package com.eatz.presentation.web.address.mapper;

import com.eatz.domain.address.Address;
import com.eatz.presentation.web.address.dto.AddressRequest;
import com.eatz.presentation.web.address.dto.AddressResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AddressMapperTest {

    private final AddressMapper mapper = new AddressMapper();

    @Test
    void shouldConvertAddressRequestToDomain() {
        AddressRequest request = new AddressRequest();
        request.setStreet("Rua A");
        request.setNumber("123");
        request.setComplement("Apto 101");
        request.setNeighbourhood("Centro");
        request.setCity("São Paulo");
        request.setState("SP");
        request.setZipCode("12345-678");

        Address result = mapper.toDomain(request);

        assertThat(result).isNotNull();
        assertThat(result.getStreet()).isEqualTo("Rua A");
        assertThat(result.getNumber()).isEqualTo("123");
        assertThat(result.getComplement()).isEqualTo("Apto 101");
        assertThat(result.getNeighbourhood()).isEqualTo("Centro");
        assertThat(result.getCity()).isEqualTo("São Paulo");
        assertThat(result.getState()).isEqualTo("SP");
        assertThat(result.getZipCode()).isEqualTo("12345-678");
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.isDeleted()).isFalse();
    }

    @Test
    void shouldConvertAddressToAddressResponse() {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Rua B");
        address.setNumber("456");
        address.setComplement("Casa");
        address.setNeighbourhood("Jardins");
        address.setCity("Rio de Janeiro");
        address.setState("RJ");
        address.setZipCode("87654-321");

        AddressResponse response = mapper.toResponse(address);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getStreet()).isEqualTo("Rua B");
        assertThat(response.getNumber()).isEqualTo("456");
        assertThat(response.getComplement()).isEqualTo("Casa");
        assertThat(response.getNeighborhood()).isEqualTo("Jardins");
        assertThat(response.getCity()).isEqualTo("Rio de Janeiro");
        assertThat(response.getState()).isEqualTo("RJ");
        assertThat(response.getZipCode()).isEqualTo("87654-321");
    }

}
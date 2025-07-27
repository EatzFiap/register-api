package com.eatz.presentation.web.customer.mapper;

import com.eatz.domain.address.Address;
import com.eatz.domain.address.CustomerAddressDetails;
import com.eatz.domain.customer.Customer;
import com.eatz.presentation.web.address.dto.AddressRequest;
import com.eatz.presentation.web.customer.dto.CustomerResponse;
import com.eatz.presentation.web.customer.dto.NewCustomerRequest;
import com.eatz.presentation.web.customer.dto.UpdateCustomerRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CustomerMapperTest {

    private CustomerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CustomerMapper();
    }

    @Nested
    @DisplayName("Mapping NewCustomerRequest to Customer")
    class MapNewCustomerRequestToCustomer {

        @Test
        void shouldMapNewCustomerRequestToCustomer() {
            NewCustomerRequest request = getNewCustomerRequestWithAddress();

            Customer result = mapper.toDomain(request);

            assertThat(result.getName()).isEqualTo("Maria");
            assertThat(result.getEmail()).isEqualTo("maria@email.com");
            assertThat(result.getPassword()).isEqualTo("123456");
            assertThat(result.getPhone()).isEqualTo("11999999999");
            assertThat(result.getCpf()).isEqualTo("12345678900");

            assertNotNull(result.getAddresses());
            Address address = result.getAddresses().getFirst();

            assertThat(address.getStreet()).isEqualTo("Rua A");
            assertThat(address.getCity()).isEqualTo("São Paulo");
            assertThat(address.getState()).isEqualTo("SP");
            assertThat(address.getZipCode()).isEqualTo("01000-000");
        }

    }

    @Nested
    @DisplayName("Mapping UpdateCustomerRequest to Customer")
    class MapUpdateCustomerRequestToCustomer {

        @Test
        void shouldMapUpdateCustomerRequestToCustomer() {
            UpdateCustomerRequest request = new UpdateCustomerRequest();
            request.setName("João");
            request.setEmail("joao@email.com");
            request.setPhone("11988887777");
            request.setCpf("98765432100");
            request.setProfileImageUrl("https://imagem.com/foto.png");

            Customer result = mapper.toDomain(request);

            assertThat(result.getName()).isEqualTo("João");
            assertThat(result.getEmail()).isEqualTo("joao@email.com");
            assertThat(result.getPhone()).isEqualTo("11988887777");
            assertThat(result.getCpf()).isEqualTo("98765432100");
            assertThat(result.getProfileImageUrl()).isEqualTo("https://imagem.com/foto.png");
        }

    }

    @Nested
    @DisplayName("Mapping Customer to CustomerResponse")
    class MapCustomerToCustomerResponse {

        @Test
        void shouldMapCustomerToCustomerResponseWithAddresses() {
            Customer customer = getCustomerWithAddress();

            CustomerResponse response = mapper.toResponse(customer);

            assertThat(response.getName()).isEqualTo("Paula");
            assertThat(response.getEmail()).isEqualTo("paula@email.com");
            assertThat(response.getPhone()).isEqualTo("11988886666");
            assertThat(response.getCpf()).isEqualTo("11223344556");
            assertThat(response.getAddresses()).hasSize(1);
            assertThat(response.getAddresses().getFirst().getCity()).isEqualTo("São Paulo");
            assertThat(response.getAddresses().getFirst().getNickname()).isEqualTo("Casa");
            assertThat(response.getAddresses().getFirst().isDefault()).isTrue();
        }

        @Test
        void shouldMapCustomerToCustomerResponseWithNullAddressList() {
            Customer customer = getCustomerWithoutAddress();
            customer.setAddresses(null);

            CustomerResponse response = mapper.toResponse(customer);

            assertThat(response.getName()).isEqualTo("Carlos");
            assertThat(response.getEmail()).isEqualTo("carlos@email.com");
            assertThat(response.getAddresses()).isEmpty();
        }

        @Test
        @DisplayName("Maps Customer to CustomerResponse with empty address list when customer addresses are empty")
        void mapsCustomerToCustomerResponseWithEmptyAddressListWhenCustomerAddressesAreEmpty() {
            Customer customer = getCustomerWithoutAddress();
            customer.setAddresses(Collections.emptyList());

            CustomerResponse response = mapper.toResponse(customer);

            assertThat(response.getName()).isEqualTo("Carlos");
            assertThat(response.getEmail()).isEqualTo("carlos@email.com");
            assertThat(response.getAddresses()).isEmpty();
        }

    }

    private static @NotNull Customer getCustomerWithoutAddress() {
        Customer customer = new Customer();
        customer.setId(2L);
        customer.setName("Carlos");
        customer.setEmail("carlos@email.com");
        customer.setPhone("11977776666");
        customer.setCpf("9988776655");
        return customer;
    }

    private static @NotNull Customer getCustomerWithAddress() {
        CustomerAddressDetails address = new CustomerAddressDetails();
        address.setId(1L);
        address.setStreet("Rua 1");
        address.setNumber("123");
        address.setComplement("Apto 45");
        address.setNeighbourhood("Centro");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("01000-000");
        address.setNickname("Casa");
        address.setDefault(true);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Paula");
        customer.setEmail("paula@email.com");
        customer.setPhone("11988886666");
        customer.setCpf("11223344556");
        customer.setAddresses(List.of(address));
        return customer;
    }

    private static @NotNull NewCustomerRequest getNewCustomerRequestWithAddress() {
        NewCustomerRequest request = new NewCustomerRequest();
        request.setName("Maria");
        request.setEmail("maria@email.com");
        request.setPassword("123456");
        request.setPhone("11999999999");
        request.setCpf("12345678900");

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setStreet("Rua A");
        addressRequest.setCity("São Paulo");
        addressRequest.setState("SP");
        addressRequest.setZipCode("01000-000");

        request.setAddress(addressRequest);
        return request;
    }

}
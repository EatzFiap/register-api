package com.eatz.presentation.web.restaurantUser.mapper;

import com.eatz.domain.address.Address;
import com.eatz.domain.restaurantUser.RestaurantUser;
import com.eatz.domain.restaurantUser.enums.RestaurantRole;
import com.eatz.presentation.web.address.dto.AddressResponse;
import com.eatz.presentation.web.restaurantUser.dto.RestaurantUserRequest;
import com.eatz.presentation.web.restaurantUser.dto.RestaurantUserResponse;
import com.eatz.presentation.web.restaurantUser.dto.UpdateRestaurantUserRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.eatz.helper.AdressHelper.createAddress;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUserMapperTest {

    private final RestaurantUserMapper mapper = new RestaurantUserMapper();

    @Test
    void shouldConvertRestaurantUserRequestToDomain() {
        RestaurantUserRequest request = new RestaurantUserRequest();
        request.setName("Maria");
        request.setEmail("maria@example.com");
        request.setPassword("123456");
        request.setPhone("11999999999");
        request.setCpf("123.456.789-00");
        request.setProfileImageUrl("https://imagem.com/maria.png");
        request.setRestaurantId(1L);
        request.setRole("ADMIN");
        request.setAddress(createAddress());

        RestaurantUser result = mapper.toDomain(request);

        assertNotNull(result);
        assertThat(result)
                .extracting(
                        RestaurantUser::getName,
                        RestaurantUser::getEmail,
                        RestaurantUser::getPassword,
                        RestaurantUser::getPhone,
                        RestaurantUser::getCpf,
                        RestaurantUser::getProfileImageUrl,
                        RestaurantUser::getRestaurantId,
                        RestaurantUser::getRole
                )
                .containsExactly(
                        "Maria",
                        "maria@example.com",
                        "123456",
                        "11999999999",
                        "123.456.789-00",
                        "https://imagem.com/maria.png",
                        1L,
                        RestaurantRole.ADMIN
                );

        assertNotNull(result.getAddress());
        assertThat(result.getAddress())
                .extracting(
                        Address::getStreet,
                        Address::getNumber,
                        Address::getCity,
                        Address::getState,
                        Address::getZipCode
                )
                .containsExactly(
                        "Rua das Flores",
                        "123",
                        "São Paulo",
                        "SP",
                        "12345-678"
                );
    }

    @Test
    void shouldConvertUpdateRestaurantUserRequestToDomain() {
        UpdateRestaurantUserRequest request = new UpdateRestaurantUserRequest();
        Address address = createAddress();
        request.setName("João");
        request.setEmail("joao@example.com");
        request.setPhone("11988888888");
        request.setCpf("987.654.321-00");
        request.setProfileImageUrl("https://imagem.com/joao.png");
        request.setRole("MANAGER");
        request.setAddress(address);

        RestaurantUser result = mapper.toDomain(request);

        assertNotNull(result);
        assertThat(result.getName()).isEqualTo("João");
        assertThat(result.getEmail()).isEqualTo("joao@example.com");
        assertThat(result.getPhone()).isEqualTo("11988888888");
        assertThat(result.getCpf()).isEqualTo("987.654.321-00");
        assertThat(result.getProfileImageUrl()).isEqualTo("https://imagem.com/joao.png");
        assertThat(result.getRole()).isEqualTo(RestaurantRole.MANAGER);

        assertNotNull(result.getAddress());
        assertThat(result.getAddress())
                .isEqualTo(address);
    }

    @Test
    void shouldConvertRestaurantUserToResponse() {
        RestaurantUser user = new RestaurantUser();
        Address address = createAddress();
        user.setId(1L);
        user.setName("Carlos");
        user.setEmail("carlos@example.com");
        user.setPhone("11977777777");
        user.setCpf("222.333.444-55");
        user.setProfileImageUrl("https://imagem.com/carlos.png");
        user.setRestaurantId(1L);
        user.setRole(RestaurantRole.EMPLOYEE);
        user.setAddress(address);

        RestaurantUserResponse response = mapper.toResponse(user);

        assertNotNull(response);
        assertThat(response)
                .extracting(
                        RestaurantUserResponse::getId,
                        RestaurantUserResponse::getName,
                        RestaurantUserResponse::getEmail,
                        RestaurantUserResponse::getPhone,
                        RestaurantUserResponse::getCpf,
                        RestaurantUserResponse::getProfileImageUrl,
                        RestaurantUserResponse::getRestaurantId,
                        RestaurantUserResponse::getRole
                )
                .containsExactly(
                        1L,
                        "Carlos",
                        "carlos@example.com",
                        "11977777777",
                        "222.333.444-55",
                        "https://imagem.com/carlos.png",
                        1L,
                        RestaurantRole.EMPLOYEE.name()
                );

        assertNotNull(response.getAddress());
        assertThat(response.getAddress())
                .extracting(
                        AddressResponse::getStreet,
                        AddressResponse::getNumber,
                        AddressResponse::getCity,
                        AddressResponse::getState,
                        AddressResponse::getZipCode
                )
                .containsExactly(
                        "Rua das Flores",
                        "123",
                        "São Paulo",
                        "SP",
                        "12345-678"
                );
    }

}
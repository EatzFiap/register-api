package com.eatz.presentation.web.customer;

import com.eatz.presentation.web.address.dto.AddressRequest;
import com.eatz.presentation.web.customer.dto.NewCustomerRequest;
import com.eatz.shared.dto.PasswordUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static com.eatz.helper.AddressHelper.createAddressRequest;
import static com.eatz.helper.AuthHelper.authenticatedRequest;
import static com.eatz.helper.CustomerHelper.createNewCustomerRequest;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.FAIL_ON_ERROR))
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CustomerControllerIT {

    @LocalServerPort
    private int port;

    private RequestSpecification authenticatedRequest;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        authenticatedRequest = authenticatedRequest("larissa.silva@email.com", "password123", "/customers/login");
    }

    @Nested
    class GetCustomerByUsername {

        @Test
        void shouldReturnCustomerDetailsByUsername() {
            authenticatedRequest
            .when()
                .get("/customers")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(10900))
                .body("name", equalTo("Larissa Silva Santos"))
                .body("email", equalTo("larissa.silva@email.com"))
                .body("cpf", equalTo("123.456.789-00"))
                .body("$", hasKey("addresses"));
        }

    }

    @Nested
    class GetCustomerById {

        @Test
        void shouldReturnCustomerDetailsById() {
            Long userId = 10900L;

            authenticatedRequest
                .pathParam("id", userId)
            .when()
                .get("/customers/{id}")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(10900))
                .body("name", equalTo("Larissa Silva Santos"))
                .body("email", equalTo("larissa.silva@email.com"))
                .body("cpf", equalTo("123.456.789-00"))
                .body("$", hasKey("addresses"));
        }

        @Test
        void shouldReturnNotFoundWhenCustomerDoesNotExist() {
            Long nonExistentUserId = 9999L;

            authenticatedRequest
                .pathParam("id", nonExistentUserId)
            .when()
                .get("/customers/{id}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Usuário não encontrado com o id: " + nonExistentUserId));
        }

    }

    @Nested
    class CreateCustomer {

        @Test
        void shouldCreateNewCustomer() {
            NewCustomerRequest request = createNewCustomerRequest();

            authenticatedRequest
                .body(request)
            .when()
                .post("/customers/register")
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("name", equalTo("Artur Silva"))
                .body("email", equalTo(request.getEmail()))
                .body("cpf", equalTo(request.getCpf()))
                .body("phone", equalTo(request.getPhone()))
                .body("$", hasKey("addresses"));
        }

        @Test
        void shouldReturnConflictWhenEmailAlreadyExists() {
            NewCustomerRequest request = createNewCustomerRequest();
            request.setEmail("larissa.silva@email.com");

            authenticatedRequest
                .body(request)
            .when()
                .post("/customers/register")
            .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("message", equalTo("Já existe um usuário com este e-mail."));
        }

    }

    @Nested
    class AddAddress {

        @Test
        void shouldAddAddressToCustomer() {
            Long customerId = 10900L;
            AddressRequest addressRequest = createAddressRequest();

            authenticatedRequest
                .pathParam("id", customerId)
                .body(addressRequest)
            .when()
                .post("/customers/{id}/address")
            .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void shouldReturnNotFoundWhenCustomerDoesNotExist() {
            Long nonExistentCustomerId = 9999L;
            AddressRequest addressRequest = createAddressRequest();

            authenticatedRequest
                .pathParam("id", nonExistentCustomerId)
                .body(addressRequest)
            .when()
                .post("/customers/{id}/address")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Usuário não encontrado com o id: " + nonExistentCustomerId));
        }

    }

    @Nested
    class UpdateAddress {

        @Test
        void shouldUpdateAddressSuccessfully() {
            Long customerId = 10901L;
            Long addressId = 10900L;
            AddressRequest addressRequest = getUpdateAddressRequest();

            authenticatedRequest
                .pathParam("customerId", customerId)
                .pathParam("addressId", addressId)
                .body(addressRequest)
            .when()
                .put("/customers/{customerId}/address/{addressId}")
            .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void shouldReturnNotFoundWhenCustomerOrAddressDoesNotExist() {
            Long nonExistentCustomerId = 9999L;
            Long nonExistentAddressId = 9999L;
            AddressRequest addressRequest = createAddressRequest();

            authenticatedRequest
                .pathParam("customerId", nonExistentCustomerId)
                .pathParam("addressId", nonExistentAddressId)
                .body(addressRequest)
            .when()
                .put("/customers/{customerId}/address/{addressId}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Usuário não encontrado com o id: " + nonExistentCustomerId));
        }

        private static @NotNull AddressRequest getUpdateAddressRequest() {
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setNickname("Updated Address");
            addressRequest.setStreet("Updated Street");
            addressRequest.setNumber("123");
            addressRequest.setComplement("Updated Complement");
            addressRequest.setNeighbourhood("Updated Neighborhood");
            addressRequest.setCity("Updated City");
            addressRequest.setState("Updated State");
            addressRequest.setZipCode("82345-678");
            addressRequest.setDefaultAddress(true);
            addressRequest.setNickname("Apartment");
            return addressRequest;
        }

    }

    @Nested
    class UpdatePassword {

        @Test
        void shouldUpdatePasswordSuccessfully() {
            String newPassword = "newPassword123";
            String oldPassword = "password123";
            PasswordUpdateRequest request = new PasswordUpdateRequest(
                    oldPassword,
                    newPassword
            );

            authenticatedRequest
                    .body(request)
                    .when()
                    .patch("/customers/password")
                    .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void shouldReturnUnauthorizedWhenOldPasswordIsIncorrect() {
            String newPassword = "newPassword123";
            String oldPassword = "wrongPassword";
            PasswordUpdateRequest request = new PasswordUpdateRequest(
                    oldPassword,
                    newPassword
            );

            authenticatedRequest
                    .body(request)
                    .when()
                    .patch("/customers/password")
                    .then()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }

    }

    @Nested
    class DeleteCustomer {

        @Test
        void shouldDeleteCustomerSuccessfully() {
            Long customerId = 10902L;

            authenticatedRequest
                .pathParam("id", customerId)
            .when()
                .delete("/customers/{id}")
            .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void shouldReturnNotFoundWhenCustomerDoesNotExist() {
            Long nonExistentCustomerId = 9999L;

            authenticatedRequest
                .pathParam("id", nonExistentCustomerId)
            .when()
                .delete("/customers/{id}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Usuário não encontrado para exclusão."));
        }

    }

    @Nested
    class DeleteAddress {

        @Test
        void shouldDeleteAddressSuccessfully() {
            Long customerId = 10901L;
            Long addressId = 10900L;

            authenticatedRequest
                .pathParam("customerId", customerId)
                .pathParam("addressId", addressId)
            .when()
                .delete("/customers/{customerId}/address/{addressId}")
            .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void shouldReturnNotFoundWhenCustomerOrAddressDoesNotExist() {
            Long nonExistentCustomerId = 9999L;
            Long nonExistentAddressId = 9999L;

            authenticatedRequest
                .pathParam("customerId", nonExistentCustomerId)
                .pathParam("addressId", nonExistentAddressId)
            .when()
                .delete("/customers/{customerId}/address/{addressId}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Usuário não encontrado com o id: " + nonExistentCustomerId));
        }

    }

}

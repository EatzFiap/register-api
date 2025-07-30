package com.eatz.presentation.web.restaurantUser;

import com.eatz.presentation.web.restaurantUser.dto.RestaurantUserRequest;
import com.eatz.shared.dto.LoginRequest;
import com.eatz.shared.dto.PasswordUpdateRequest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static com.eatz.helper.AuthHelper.authenticatedRestaurantUserRequest;
import static com.eatz.helper.RestaurantUserHelper.createRestaurantUserRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.FAIL_ON_ERROR))
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestaurantUserControllerIT {

    @LocalServerPort
    private int port;

    private RequestSpecification authenticatedRequest;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        authenticatedRequest = authenticatedRestaurantUserRequest("natalia@bellanapoli.com", "password123");
    }

    @Nested
    class GetRestaurantUserByUsername {

        @Test
        void shouldReturnRestaurantUserByUsername() {
           String username = "natalia@bellanapoli.com";

            authenticatedRequest
            .when()
                .get("/restaurant-users")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(10900))
                .body("name", equalTo("Natalia Rossi"))
                .body("email", equalTo(username))
                .body("role", equalTo("ADMIN"));
        }

    }

    @Nested
    class GetRestaurantUserById {

        @Test
        void shouldReturnRestaurantUserById() {
            Long userId = 10900L;

            authenticatedRequest
                .pathParam("id", userId)
            .when()
                .get("/restaurant-users/{id}")
            .then()
                .statusCode(200)
                .body("id", equalTo(userId.intValue()))
                .body("name", equalTo("Natalia Rossi"))
                .body("email", equalTo("natalia@bellanapoli.com"))
                .body("role", equalTo("ADMIN"));
        }

        @Test
        void shouldReturnNotFoundWhenUserDoesNotExist() {
            Long nonExistentUserId = 99999L;

            authenticatedRequest
                .pathParam("id", nonExistentUserId)
            .when()
                .get("/restaurant-users/{id}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Usuário não encontrado com id: " + nonExistentUserId));
        }

    }

    @Nested
    class CreateRestaurantUser {

        @Test
        void shouldCreateRestaurantUser() {
            RestaurantUserRequest request = createRestaurantUserRequest();

            given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
            .when()
                .post("/restaurant-users/register")
            .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("name", equalTo(request.getName()))
                .body("email", equalTo(request.getEmail()))
                .body("role", equalTo(request.getRole()))
                .body("cpf", equalTo(request.getCpf()))
                .body("phone", equalTo(request.getPhone()))
                .body("profileImageUrl", equalTo(request.getProfileImageUrl()))
                .body("address", hasKey("id"));
        }

        @Test
        void shouldReturnBadRequestWhenEmailAlreadyExists() {
            RestaurantUserRequest request = createRestaurantUserRequest();
            request.setEmail("natalia@bellanapoli.com");

            given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
            .when()
                .post("/restaurant-users/register")
            .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("message", equalTo("Este e-mail já está em uso."))
                .body("error", equalTo("Conflict"))
                .body("status", equalTo(HttpStatus.CONFLICT.value()));
        }

    }

    @Nested
    class Login {

        @Test
        void shouldSuccessfullyAuthenticateUserWhenValidCredentialsProvided() {
            String email = "natalia@bellanapoli.com";
            String password = "password123";

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new LoginRequest(email, password))
                    .when()
                    .post("/restaurant-users/login")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("$", hasKey("token"))
                    .body("$", hasKey("expiresAt"))
                    .body("type", equalTo("Bearer"))
                    .body("username", equalTo(email));
        }

        @Test
        void shouldReturnUnauthorizedWhenInvalidCredentialsProvided() {
            String email = "natalia@bellanapoli.com";
            String password = "wrongpassword";

            given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(new LoginRequest(email, password))
                    .when()
                    .post("/restaurant-users/login")
                    .then()
                    .statusCode(HttpStatus.UNAUTHORIZED.value());
        }

        @Test
        void shouldReturnNotFoundWhenUserDoesNotExist() {
            String email = "nonexistentuser@email.com";
            String password = "password123";

            given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new LoginRequest(email, password))
            .when()
                .post("/restaurant-users/login")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("User not found"));
        }

    }

    @Nested
    class UpdateRestaurantUser {

        @Test
        void shouldUpdateRestaurantUser() {
            Long userId = 10901L;
            RestaurantUserRequest request = createRestaurantUserRequest();
            request.setName("Updated Name");

            authenticatedRequest
                .pathParam("id", userId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
            .when()
                .put("/restaurant-users/{id}")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(userId.intValue()))
                .body("name", equalTo("Updated Name"))
                .body("email", equalTo(request.getEmail()))
                .body("role", equalTo(request.getRole()))
                .body("cpf", equalTo(request.getCpf()))
                .body("phone", equalTo(request.getPhone()))
                .body("profileImageUrl", equalTo(request.getProfileImageUrl()))
                .body("address", hasKey("id"));
        }

        @Test
        void shouldReturnNotFoundWhenUpdatingNonExistentUser() {
            Long nonExistentUserId = 99999L;
            RestaurantUserRequest request = createRestaurantUserRequest();

            authenticatedRequest
                .pathParam("id", nonExistentUserId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
            .when()
                .put("/restaurant-users/{id}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Usuário não encontrado com id: " + nonExistentUserId));
        }

    }

    @Nested
    class UpdatePassword {

        @Test
        void shouldUpdatePasswordWhenOldPasswordIsValid() {
            String newPassword = "newPassword123";

            authenticatedRequest
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new PasswordUpdateRequest("password123", newPassword))
            .when()
                .patch("/restaurant-users/password")
            .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void shouldReturnUnauthorizedWhenOldPasswordIsIncorrect() {
            String newPassword = "newPassword123";

            authenticatedRequest
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new PasswordUpdateRequest("wrongPassword", newPassword))
            .when()
                .patch("/restaurant-users/password")
            .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
        }

    }

    @Nested
    class Delete {

        @Test
        void shouldDeleteRestaurantUser() {
            Long userId = 10902L;

            authenticatedRequest
                .pathParam("id", userId)
            .when()
                .delete("/restaurant-users/{id}")
            .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
        }

        @Test
        void shouldReturnNotFoundWhenDeletingNonExistentUser() {
            Long nonExistentUserId = 99999L;

            authenticatedRequest
                .pathParam("id", nonExistentUserId)
            .when()
                .delete("/restaurant-users/{id}")
            .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Usuário não encontrado para exclusão."))
                .body("error", equalTo("Not Found"))
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()));
        }

    }

}

package com.eatz.presentation.web.menuItem;

import com.eatz.presentation.web.menuItem.dto.MenuItemRequest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import static com.eatz.helper.AuthHelper.authenticatedRequest;
import static com.eatz.helper.MenuItemHelper.createMenuItemRequest;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.FAIL_ON_ERROR))
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class MenuItemControllerIT {

    @LocalServerPort
    private int port;

    private RequestSpecification authenticatedRequest;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        authenticatedRequest = authenticatedRequest("natalia@bellanapoli.com", "password123", "/restaurant-users/login");
    }

    @Nested
    class FindById {

        @Test
        void shouldReturnMenuItemById() {
            Long menuItemId = 10900L;

            authenticatedRequest
                .pathParam("menuItemId", menuItemId)
            .when()
                .get("/menu-items/{menuItemId}")
            .then()
                .statusCode(200)
                .body("id", equalTo(10900))
                .body("name", equalTo("Pizza Margherita"))
                .body("description", equalTo("Pizza com molho de tomate e mussarela"))
                .body("price", equalTo(45.9f))
                .body("restaurantId", equalTo(10900))
                .body("onlyLocalConsumption", equalTo(false));
        }

        @Test
        void shouldReturn404WhenMenuItemNotFound() {
            Long nonExistentMenuItemId = 99999L;

            authenticatedRequest
                .pathParam("id", nonExistentMenuItemId)
            .when()
                .get("/menu-items/{id}")
            .then()
                .statusCode(404)
                .body("message", equalTo("Item do cardápio não encontrado com id: " + nonExistentMenuItemId));
        }

    }

    @Nested
    class FindByRestaurant {

        @Test
        void shouldReturnMenuItemsByRestaurant() {
            Long restaurantId = 10900L;

            authenticatedRequest
                .pathParam("restaurantId", restaurantId)
            .when()
                .get("/menu-items/restaurant/{restaurantId}")
            .then()
                .statusCode(200)
                .body("[0].id", equalTo(10900))
                .body("[0].name", equalTo("Pizza Margherita"))
                .body("[0].description", equalTo("Pizza com molho de tomate e mussarela"))
                .body("[0].price", equalTo(45.9f))
                .body("[0].restaurantId", equalTo(10900))
                .body("[0].onlyLocalConsumption", equalTo(false));
        }

        @Test
        void shouldReturnEmptyListWhenNoMenuItemsFoundForRestaurant() {
            Long nonExistentRestaurantId = 99999L;

            authenticatedRequest
                .pathParam("restaurantId", nonExistentRestaurantId)
            .when()
                .get("/menu-items/restaurant/{restaurantId}")
            .then()
                .statusCode(200)
                .body("$", hasSize(0));
        }

    }

    @Nested
    class CreateMenuItem {

        @Test
        void shouldCreateMenuItem() {
            MenuItemRequest requestBody = createMenuItemRequest();

            authenticatedRequest
                .body(requestBody)
            .when()
                .post("/menu-items")
            .then()
                .statusCode(200)
                .body("name", equalTo("Pizza Calabresa"))
                .body("description", equalTo("Pizza com molho de tomate, mussarela e calabresa"))
                .body("price", equalTo(49.9f))
                .body("restaurantId", equalTo(10900))
                .body("createdAt", notNullValue())
                .body("onlyLocalConsumption", equalTo(false));
        }

        @Test
        void shouldReturn400WhenCreatingMenuItemWithInvalidData() {
            String requestBody = """
                {
                    "name": "",
                    "description": "",
                    "price": -10.0,
                    "restaurantId": 10900,
                    "onlyLocalConsumption": false,
                    "photoBase64": null,
                    "photoContentType": null
                }
                """;

            authenticatedRequest
                .body(requestBody)
            .when()
                .post("/menu-items")
            .then()
                .statusCode(400)
                .body("message", equalTo("Validation failed"))
                .body("details", notNullValue());
        }

    }

    @Nested
    class UpdateMenuItem {

        @Test
        void shouldUpdateMenuItem() {
            Long menuItemId = 10900L;
            MenuItemRequest requestBody  = new MenuItemRequest(
                    "Pizza Calabresa Atualizada",
                    "Pizza com molho de tomate, mussarela e calabresa",
                    49.9,
                    false,
                    null,
                    null,
                    10900L
            );

            authenticatedRequest
                    .pathParam("id", menuItemId)
                    .body(requestBody)
                    .when()
                    .put("/menu-items/{id}")
                    .then()
                    .statusCode(200)
                    .body("id", equalTo(10900))
                    .body("name", equalTo("Pizza Calabresa Atualizada"))
                    .body("description", equalTo("Pizza com molho de tomate, mussarela e calabresa"))
                    .body("price", equalTo(49.9f))
                    .body("restaurantId", equalTo(10900))
                    .body("onlyLocalConsumption", equalTo(false));
        }

        @Test
        void shouldReturn404WhenUpdatingNonExistentMenuItem() {
            Long nonExistentMenuItemId = 99999L;
            MenuItemRequest requestBody = createMenuItemRequest();

            authenticatedRequest
                .pathParam("id", nonExistentMenuItemId)
                .body(requestBody)
            .when()
                .put("/menu-items/{id}")
            .then()
                .statusCode(404)
                .body("message", equalTo("Item do cardápio não encontrado com id: " + nonExistentMenuItemId));
        }

    }

    @Nested
    class DeleteMenuItem {

        @Test
        void shouldDeleteMenuItem() {
            Long menuItemId = 10900L;

            authenticatedRequest
                .pathParam("id", menuItemId)
            .when()
                .delete("/menu-items/{id}")
            .then()
                .statusCode(204);
        }

        @Test
        void shouldReturn404WhenDeletingNonExistentMenuItem() {
            Long nonExistentMenuItemId = 99999L;

            authenticatedRequest
                .pathParam("id", nonExistentMenuItemId)
            .when()
                .delete("/menu-items/{id}")
            .then()
                .statusCode(404)
                .body("message", equalTo("Item do cardápio não encontrado com id: " + nonExistentMenuItemId));
        }

    }

}